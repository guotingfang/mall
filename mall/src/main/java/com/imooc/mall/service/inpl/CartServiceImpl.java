package com.imooc.mall.service.inpl;

import com.google.gson.Gson;
import com.imooc.mall.dao.ProductMapper;
import com.imooc.mall.enums.ProductStatusEnum;
import com.imooc.mall.form.CartAddForm;
import com.imooc.mall.form.CartUpdateForm;
import com.imooc.mall.pojo.Cart;
import com.imooc.mall.pojo.Product;
import com.imooc.mall.service.ICartService;
import com.imooc.mall.vo.CartProductVo;
import com.imooc.mall.vo.CartVo;
import com.imooc.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

import static com.imooc.mall.enums.ResponseEnum.*;

/**
 * Greated by Guo
 *
 * @date2020/5/13 15:46
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Gson gson = new Gson();

    /**
     * 往购物车商品
     *
     * @param cartform
     * @return
     */
    @Override
    public ResponseVo<CartVo> addCart(Integer uid, CartAddForm cartform) {
        Product product = productMapper.selectByPrimaryKey(cartform.getProductId());
        Cart cart;
        Integer quantity = 1;
        //商品是否存在
        if (product == null) {
            return ResponseVo.error(PRODUCT_NOT_EXIST);
        }
        //商品的状态是不是在售
        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }
        //商品库存是否充足
        if (product.getStock() <= 0) {
            return ResponseVo.error(PRODUCT_STOCK_ERROR);
        }
        //写入redis
        //key/: cart_1
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);

        String value = opsForHash.get(redisKey, String.valueOf(cartform.getProductId()));
        if (StringUtils.isEmpty(value)) {
            //如果等于true说明购物车没有改商品，需要添加
            cart = new Cart(product.getId(), quantity, cartform.getSelected());
        } else {
            //如果有该商品，需要数量+1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }
        opsForHash.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        return list(uid);
    }


    /**
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        Set<Integer> productIdSet = new HashSet<>();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        CartVo cartVo = new CartVo();
        boolean selected = true;
        Integer totalQuantity = 0;
        BigDecimal bigDecimal = BigDecimal.ZERO;

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            productIdSet.add(Integer.valueOf(entry.getKey()));
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            CartProductVo cartProductVo = new CartProductVo();
            cartProductVo.setProductId(cart.getProductId());
            cartProductVo.setQuantity(cart.getQuantity());
            cartProductVo.setProductSelected(cart.getProductSelected());
            cartProductVoList.add(cartProductVo);
            totalQuantity += cart.getQuantity();
        }

        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);

        if (productList != null && !productList.isEmpty()) {
            for (Product product : productList) {
                for (CartProductVo cartProductVo : cartProductVoList) {
                    if (product.getId().equals(cartProductVo.getProductId())) {
                        cartProductVo.setProductName(product.getName());
                        cartProductVo.setProductSubtitle(product.getSubtitle());
                        cartProductVo.setProductMainImage(product.getMainImage());
                        cartProductVo.setProductPrice(product.getPrice());
                        cartProductVo.setProductStatus(product.getStatus());
                        cartProductVo.setProductTotalPrice(BigDecimal.valueOf(cartProductVo.getQuantity()).multiply(product.getPrice()));
                        cartProductVo.setProductStock(product.getStock() > 100 ? 100 : product.getStock());
                        if (!cartProductVo.getProductSelected()) {
                            selected = false;
                        }
                        if (cartProductVo.getProductSelected()) {
                            bigDecimal = bigDecimal.add(cartProductVo.getProductTotalPrice());
                        }
                    }
                }
            }
        }
        cartVo.setSelectedAll(selected);
        cartVo.setCartTotalPrice(bigDecimal);
        cartVo.setCartTotalQuantity(totalQuantity);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }

    /**
     * 更新购物车
     * @param uid
     * @param cartUpdateForm 非必填，
     * @return
     */
    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm cartUpdateForm) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = hashOperations.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        //如果已经有了，修改内容
        Cart cart = gson.fromJson(value, Cart.class);
        if (cartUpdateForm.getQuantity() != null
                && cartUpdateForm.getQuantity() > 0) {
            cart.setQuantity(cartUpdateForm.getQuantity());
        }
        if (cartUpdateForm.getSelected() != null) {
            cart.setProductSelected(cartUpdateForm.getSelected());
        }
        hashOperations.put(redisKey, String.valueOf(cart.getProductId()), gson.toJson(cart));
        return list(uid);
    }

    /**
     * 删除购物车某商品
     *
     * @param uid
     * @param productId
     * @return
     */
    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String reidsKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        String value = hashOperations.get(reidsKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(CART_PRODUCT_NOT_EXIST);
        }
        hashOperations.delete(reidsKey, String.valueOf(productId));
        return list(uid);
    }

    /**
     * 购物车商品全选
     *
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String reidsKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(true);
            hashOperations.put(reidsKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
        }

        return list(uid);
    }

    /**
     * 全部不选中
     *
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String reidsKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        for (Cart cart : listForCart(uid)) {
            cart.setProductSelected(false);
            hashOperations.put(reidsKey,String.valueOf(cart.getProductId()),gson.toJson(cart));
        }

        return list(uid);
    }

    /**
     * 获取购物中所有商品数量总和
     *
     * @param uid
     * @return
     */
    @Override
    public ResponseVo<Integer> sum(Integer uid) {
//        Integer sum = listForCart(uid).stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        Integer sum =0;
        for (Cart cart : listForCart(uid)) {
            sum +=  cart.getQuantity();
        }

        return ResponseVo.success(sum);

    }

    /**
     * 获取redis里面的value集合
     * @param uid
     * @return
     */
    public List<Cart> listForCart(Integer uid){
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String reidsKey = String.format(CART_REDIS_KEY_TEMPLATE, uid);
        Map<String, String> cartMap = hashOperations.entries(reidsKey);
        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : cartMap.entrySet()) {
            cartList.add(gson.fromJson(entry.getValue(), Cart.class));
        }
        return cartList;
    }
}
