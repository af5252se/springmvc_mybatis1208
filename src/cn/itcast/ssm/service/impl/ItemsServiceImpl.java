package cn.itcast.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.controller.exception.CustomException;
import cn.itcast.ssm.mapper.ItemsMapper;
import cn.itcast.ssm.mapper.ItemsMapperCustom;
import cn.itcast.ssm.po.Items;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

public class ItemsServiceImpl implements ItemsService{
	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	@Override
	public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo)
			throws Exception {
		// TODO Auto-generated method stub
		return itemsMapperCustom.findItemsList(itemsQueryVo) ;
	}
	
	@Autowired
	private ItemsMapper itemsMapper;
	@Override
	public ItemsCustom findItemsById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		Items items = itemsMapper.selectByPrimaryKey(id);
		ItemsCustom itemsCustom=null;
		if(items==null){
			throw new CustomException("您查询的商品信息不存在");
		}
		//为了让两者类型上不冲突  使用spring提供的BeanUtils拷贝方法
		if(items!=null){
			itemsCustom=new ItemsCustom();
			BeanUtils.copyProperties(items, itemsCustom);
		}
		
		return itemsCustom;
	}
	//使用更新操作时，如果根据id进行更新操作时，必须更具规范传入一个id，这是为了让别人更好的使用这个接口，知道要传入一个id
	@Override
	public void updateItems(Integer id, ItemsCustom itemsCustom) throws Exception {
		// TODO Auto-generated method stub
		
		//使用updateByPrimaryKeyWithBLOBs方法进行更新操作时，必须要设置id，这个方法会根据id去更新items所有字段，包括大文本类型
		itemsCustom.setId(id);
		itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
	}
//	@Autowired
//	private ItemsQueryVo itemsQueryVo;
//	public void deleteItems(ItemsQueryVo itemsQueryVo) throws Exception {
//		// TODO Auto-generated method stub
//		itemsMapperCustom.deleteItemsByMoreId(itemsQueryVo.getIds());
//	}
//	
//	public void deleteItems(ItemsQueryVo itemsQueryVo) throws Exception {
//		// TODO Auto-generated method stub
////		Items items = new Items();
////		List<Integer> ids= new ArrayList<Integer>();
////		for (int i=0;i<itemsQueryVo.getIds.length;i++) {
////			ids.add(items_id[i]);
////		}
////		items.setIds(ids);
//		
//		itemsMapperCustom.deleteItemsByMoreId(itemsQueryVo.getIds());
//		
//	}
//	public void deleteItems(ItemsQueryVo itemsQueryVo) throws Exception {
//		// TODO Auto-generated method stub
//		itemsMapperCustom.deleteItemsByMoreId(itemsQueryVo.getIds());
//	}
//	@Override
//	public void deleteItems(ItemsQueryVo itemsQueryVo) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//	@Override
//	public void deleteItems(ItemsQueryVo itemsQueryVo) throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
	@Override
	public void deleteItems(Integer[] items_id) throws Exception {
		// TODO Auto-generated method stub
		ItemsQueryVo itemsQueryVo=new ItemsQueryVo();
		List<Integer> ids= new ArrayList<Integer>();
		for (int i=0;i<items_id.length;i++) {
			ids.add(items_id[i]);
		}
		itemsQueryVo.setIds(ids);
		itemsMapperCustom.deleteItemsByMoreId(itemsQueryVo);
	}
	
//	public void deleteItems(ItemsQueryVo itemsQueryVo) throws Exception {
//		// TODO Auto-generated method stub
////		itemsMapperCustom.deleteItemsByMoreId(itemsQueryVo.getIds());
//	}
	
	
	
	

}
