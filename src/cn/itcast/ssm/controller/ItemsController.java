package cn.itcast.ssm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.controller.exception.CustomException;
import cn.itcast.ssm.controller.validation.ValidGroup1;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

@Controller
@RequestMapping("/items")
public class ItemsController {
	@Autowired
	private ItemsService itemsService;
	//商品分类
		@ModelAttribute("itemtypes")
		public Map<String, String> getItemTypes(){
			
			Map<String, String> itemTypes = new HashMap<String,String>();
			itemTypes.put("101", "数码");
			itemTypes.put("102", "母婴");
			
			return itemTypes;
		}

	//商品查询
	@RequestMapping("/queryItems")
	public ModelAndView queryItems(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception{
		// 商品列表
		
		System.out.println(request.getParameter("id"));
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

		
		// 创建modelAndView准备填充数据、设置视图
		ModelAndView modelAndView = new ModelAndView();

		// 填充数据
		modelAndView.addObject("itemsList", itemsList);
		// 视图
		modelAndView.setViewName("items/itemsList");

		return modelAndView;
	}
	//查询商品信息输出json
	@RequestMapping("/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsView(@PathVariable Integer id) throws Exception{
		
		ItemsCustom itemsCustom=itemsService.findItemsById(id);
		
		return itemsCustom;
	}
	
	//商品信息修改页面显示
	/*@RequestMapping(value="/editItems",method=)*/
	@RequestMapping(value="/editItems",method={RequestMethod.GET,RequestMethod.POST})
	public String editItems(Model model,@RequestParam(value="id",required=true ) Integer items_id) throws Exception{
//		ModelAndView modelAndView = new ModelAndView();
		ItemsCustom itemsCustom=itemsService.findItemsById(items_id);
		//判断商品是否为空，抛出异常提示用户
//		if(itemsCustom==null){
//			throw new CustomException("您查询的商品信息不存在");
//		}
		model.addAttribute("items", itemsCustom);
		/*modelAndView.addObject("itemsCustom", itemsCustom);
		modelAndView.setViewName("items/editItems");*/
		return "items/editItems";
	}
	
	//商品信息提交
	@RequestMapping("/editItemsSubmit")
	public String editItemsSubmit(Model model,HttpServletRequest request,HttpServletResponse response,Integer id,
			@ModelAttribute("items") @Validated(value={ValidGroup1.class}) ItemsCustom itemsCustom,BindingResult bindingResult,
			MultipartFile items_pic
			)throws Exception{
		
		//获取错误信息
		if(bindingResult.hasErrors()){
			//输出错误信息
			List<ObjectError> allErrors=bindingResult.getAllErrors();
			List<String> listErrors=new ArrayList<String>();
			for(ObjectError objectError : allErrors){
				String strErrors=new String(objectError.getDefaultMessage().getBytes("ISO-8859-1"),"UTF-8");
				System.out.println(strErrors);
				listErrors.add(strErrors);
				
			}
			model.addAttribute("allErrors",listErrors);
			return "items/editItems";
		}
		//上传图片
		String originalFilename=items_pic.getOriginalFilename();
		if(items_pic!=null&&originalFilename!=null&&originalFilename.length()>0){
			
			String pic_path="F:\\upload\\";
			
			String newFileName=UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
			File newFile=new File(pic_path+newFileName);
			items_pic.transferTo(newFile);
			itemsCustom.setPic(newFileName);
//			System.out.println(newFileName);
//			System.out.println(originalFilename.substring(originalFilename.lastIndexOf(".")));
		}
		
		//调用service方法更改商品信息
//		if(itemsCustom.getPic()!=null)
		itemsService.updateItems(id, itemsCustom);
		
//		ModelAndView modelAndView = new ModelAndView();

//		return "redirect:/items/queryItems.action";
		return "success";
	}
	//批量删除
	@RequestMapping("/deleteItems")
	public String deleterItems(Integer [] items_id) throws Exception{
		//调用service方法。。。
		itemsService.deleteItems(items_id);
		return "success";
		
		
	}
	
	//批量修改查询
	
	@RequestMapping("/editItemsQuery")
	public ModelAndView editItemsQuery(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception{
		// 商品列表
		
		
		List<ItemsCustom> itemsList = itemsService.findItemsList(itemsQueryVo);

		
		// 创建modelAndView准备填充数据、设置视图
		ModelAndView modelAndView = new ModelAndView();

		// 填充数据
		modelAndView.addObject("itemsList", itemsList);
		// 视图
		modelAndView.setViewName("items/editItemsQuery");

		return modelAndView;
	}
	
	//批量修改提交
	@RequestMapping("/editItemsAllSubmit")
	public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo)throws Exception{
		return "success";
		
	}
}
