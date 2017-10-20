package cn.itcast.ssm.po;

import java.util.List;

public class ItemsQueryVo {
	//商品信息
	private Items items;
		//为了系统可扩展性，对原始po类进行扩展
	private List<Integer> ids;
	
	//批量修改
	private List<ItemsCustom> itemsList;
	public List<ItemsCustom> getItemsList() {
		return itemsList;
	}
	public void setItemsList(List<ItemsCustom> itemsList) {
		this.itemsList = itemsList;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	@Override
	public String toString() {
		return "ItemsQueryVo [items=" + items + ", ids=" + ids
				+ ", itemsCustom=" + itemsCustom + "]";
	}
	
	
	public List<Integer> getIds() {
		return ids;
	}


	private ItemsCustom itemsCustom;
	public Items getItems() {
		return items;
	}
	public void setItems(Items items) {
		this.items = items;
	}
	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}
	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}
}
