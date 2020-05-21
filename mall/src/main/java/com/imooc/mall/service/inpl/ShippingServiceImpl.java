package com.imooc.mall.service.inpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.dao.ShippingMapper;
import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.form.ShippingForm;
import com.imooc.mall.pojo.Shipping;
import com.imooc.mall.service.IShippingService;
import com.imooc.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.imooc.mall.enums.ResponseEnum.DELETE_SHIPPING_FAIL;
import static com.imooc.mall.enums.ResponseEnum.ERROR;

/**
 * Greated by Guo
 *
 * @date2020/5/18 8:43
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;
    /**
     * 添加地址
     *
     * @param shippingForm 表单验证类
     * @return
     */
    @Override
    public ResponseVo<Map<String, Integer>> addShipping(ShippingForm shippingForm, Integer userId) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm, shipping);
        shipping.setUserId(userId);
        int i = shippingMapper.insertSelective(shipping);
        if ( i <= 0 ){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("shippingId",shipping.getId());
        return ResponseVo.success(map);
    }

    /**
     * 删除地址
     *
     * @param shippingId
     * @return
     */
    @Override
    public ResponseVo deleteshipping(Integer uid, Integer shippingId) {
        int column = shippingMapper.selectByIdAndUid(uid, shippingId);
        if (column < 0 ){
           return ResponseVo.error(ERROR);
        }
        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if (row == 0){
            return ResponseVo.error(DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success();
    }


    /**
     *修改地址
     * @param uid  用户ID
     * @param shippingId  地址ID
     * @param shippingForm   表单
     * @return
     */
    @Override
    public ResponseVo updateshipping(Integer uid, Integer shippingId, ShippingForm shippingForm) {
        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(shippingForm,shipping);
        shipping.setId(shippingId);
        shipping.setUserId(uid);
        int column = shippingMapper.selectByIdAndUid(uid, shippingId);
        if (column < 0 ){
            return ResponseVo.error(ERROR);
        }
        int row = shippingMapper.updateByPrimaryKeySelective(shipping);
        if (row == 0){
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();
    }

    /**
     * 获取地址列表
     *
     * @param uid  用户id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseVo<PageInfo> listShipping(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(uid);
        PageInfo pageInfo = new PageInfo(shippingList);

        return ResponseVo.success(pageInfo);
    }
}
