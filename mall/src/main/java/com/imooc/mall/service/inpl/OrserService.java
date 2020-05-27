package com.imooc.mall.service.inpl;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imooc.mall.dao.OrderItemMapper;
import com.imooc.mall.dao.OrderMapper;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.dao.ShippingMapper;
import com.imooc.mall.enums.OrderStatusEnum;
import com.imooc.mall.enums.PaymentTypeEnum;
import com.imooc.mall.enums.ProductStatusEnum;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.pojo.*;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.service.IOrderService;
import com.imooc.mall.vo.OrderItemVo;
import com.imooc.mall.vo.OrderVo;
import com.imooc.mall.vo.ResponseVo;
import com.imooc.mall.vo.ShippingVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Greated by Guo
 *
 * @date2020/5/21 10:34
 */
@Service
@Slf4j
public class OrserService implements IOrderService {

    @Autowired
    private ShippingMapper shippingMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ICartService cartService;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    /**
     * 新建订单
     * @param uid
     * @param shippingId
     * @return
     */
    @Override
    @Transactional
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
            //收获地址检验（总之要查出来的）
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if (shipping == null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        //获取购物车，检验（是否有商品，库存）
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        Set<Integer> productIdSet = cartList.stream()
                .map(Cart::getProductId)
                .collect(Collectors.toSet());
        List<Product> products = productMapper.selectByProductIdSet(productIdSet);
        Map<Integer, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Product -> Product));
        //商品检验
        List<OrderItem> orderItemList = new ArrayList<>();
        //获得订单号
        Long orderNo = generateOrderNo();
        for (Cart cart : cartList) {
            Product product = productMap.get(cart.getProductId());
            if (product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,
                        "商品不存在，productId = "+cart.getProductId());
            }
            //校验商品的上下架状态
            if (!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,
                        "商品不是在售状态"+product.getName());
            }
            //判断商品数量
            if (cart.getQuantity() > product.getStock()){
                ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,
                        "商品库存不足"+product.getName());
            }
            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);
            //减少库存
            product.setStock(product.getStock() - cart.getQuantity());
            int row = productMapper.updateByPrimaryKeySelective(product);
            if (row <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }
        }
        //计算总价，只计算选中的商品
        //生成订单，入库：order和order——item，事务控制
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        //更新购物车
        for (Cart cart : cartList) {
            cartService.delete(uid, cart.getProductId());
        }
        //构造roderVo
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 获取订单List
     * @param uid
     * @param pageNum
     * @param pageSize
     * @return
     */

    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        List<Order> orderList = orderMapper.selectByUId(uid);
        Set<Long> orderNoSet = orderList.stream()
                .map(Order::getOrderNo).collect(Collectors.toSet());
        Set<Integer> shippingIdSet = orderList.stream()
                .map(Order::getShippingId).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        Map<Long, List<OrderItem>> ordeItemrMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));
        Map<Integer, Shipping> shippingMap = shippingList.stream()
                .collect(Collectors.toMap(Shipping::getId, Shipping -> Shipping));
        List<OrderVo> orderVoList = new ArrayList<>();
        for (Order order : orderList){
            orderVoList.add(buildOrderVo(order,
                    ordeItemrMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId())));
        }
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ResponseVo.success(pageInfo);
    }

    /**
     * 订单详情
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)){
            ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNo(orderNo);

        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 删除订单
     * @param uid
     * @param orderNo
     * @return
     */
    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null || !order.getUserId().equals(uid)){
            ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    /**
     * 修改订单状态
     *
     * @param orderNo
     */
    @Override
    public void paid(Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (order == null){
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc()+"订单id："+orderNo);
        }
        //只有未付款订单可以改成已付款
       if (!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            throw new RuntimeException(ResponseEnum.ORDER_NOT_EXIST.getDesc()+"订单id："+orderNo);
        }
        order.setStatus(OrderStatusEnum.PAID.getCode());
        order.setPaymentTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0){
            throw new RuntimeException("将订单跟新为已支付状态失败，订单id:"+orderNo);
        }
    }

    /**
     * 构造OrderVo
     * @param order
     * @param orderItemList
     * @param shipping
     * @return
     */
    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        ShippingVo shippingVo = new ShippingVo();
        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(order,orderVo);
        BeanUtils.copyProperties(shipping, shippingVo);
        orderVo.setOrderIetmVoList(orderItemVoList);
        if (shippingVo != null){
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shippingVo);
        }
        return orderVo;
    }
    /**
     * 构造OrderVo
     * @param order
     * @param orderItemList
     * @return
     */
    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList) {
        OrderVo orderVo = new OrderVo();
        ShippingVo shippingVo = new ShippingVo();
        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(order,orderVo);
        orderVo.setOrderIetmVoList(orderItemVoList);
        orderVo.setShippingVo(null);
        return orderVo;
    }
    /**
     *
     *  构造Order
     * @param uid
     * @param orderNo
     * @param shippingId
     * @param orderItems
     * @return
     */
    private Order buildOrder(Integer uid, Long orderNo,Integer shippingId,List<OrderItem> orderItems) {
        BigDecimal payment = orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());
        return order;
    }


    /**
     * 构造OrderItem
     * @param uid
     * @param orderNo
     * @param quantity
     * @param product
     * @return
     */
    private OrderItem buildOrderItem(Integer uid, Long orderNo,Integer quantity, Product product){
        OrderItem orderItem = new OrderItem();
        orderItem.setUserId(uid);
        orderItem.setOrderNo(orderNo);
        orderItem.setProductId(product.getId());
        orderItem.setProductName(product.getName());
        orderItem.setProductImage(product.getMainImage());
        orderItem.setCurrentUnitPrice(product.getPrice());
        orderItem.setQuantity(quantity);
        orderItem.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return orderItem;
    }

    /**
     * 获取orderno 订单编号
     * @return
     */
    private Long generateOrderNo(){
        return System.currentTimeMillis()+new Random().nextInt(999);
    }

}
