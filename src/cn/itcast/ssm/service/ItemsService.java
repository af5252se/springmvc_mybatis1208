package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;

public interface ItemsService {
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;
	
	//查询商品信息,根据id查
	public ItemsCustom findItemsById(Integer id) throws Exception;
	
	//修改商品信息
	
	public void updateItems(Integer id,ItemsCustom itemsCustom) throws Exception;
	//批量删除
	public void deleteItems(Integer [] items_id)throws Exception;
}
