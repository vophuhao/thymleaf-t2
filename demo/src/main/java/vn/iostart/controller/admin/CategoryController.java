package vn.iostart.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iostart.entity.CategoryEntity;
import vn.iostart.service.Imp.ICategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired
	ICategoryService categoryService;

	/*
	 * @GetMapping("add") public String add(ModelMap model) { CategoryModel
	 * cateModel = new CategoryModel(); cateModel.setIsEdit(false); // chuyền dữ
	 * liệu từ model vào biến category để đưa lên view
	 * model.addAttribute("category", cateModel); return
	 * "admin/categories/addOrEdit";
	 * 
	 * }
	 * 
	 * @PostMapping("saveOrUpdate") public ModelAndView saveOrUpdate(ModelMap model,
	 * 
	 * @Valid @ModelAttribute("category") CategoryModel cateMdoel, BindingResult
	 * result) { if (result.hasErrors()) { return new
	 * ModelAndView("admin/categories/addOrEdit"); } CategoryEntity entity = new
	 * CategoryEntity(); //copy từ Model sang Entity BeanUtils.copyProperties
	 * (cateMdoel, entity); // gọi hàm save trong service
	 * categoryService.save(entity); //đưa thông báo về cho biến message String
	 * message= ""; if(cateMdoel.getIsEdit() == true) {
	 * message="Category is Edited!!!!!!!!"; }
	 * 
	 * else { } message="Category is saved!!!!!!!!"; } model.addAttribute("message",
	 * message); //redirect ve URL controller return new
	 * ModelAndView("forward:/admin/categories/searchpaginated", model); }
	 */
	@RequestMapping("")
	public String list(ModelMap model) {

	List<CategoryEntity> list = categoryService.findAll();

	model.addAttribute("categories", list);
	return "admin/list";

	/*@GetMapping("edit/{categoryId)")
	public ModelAndView edit (ModelMap model, @PathVariable("categoryId") Long categoryId) {
		Optional<CategoryEntity> optCategory categoryService.findById(categoryId);
		Category Model cateHodel = new CategoryModel();

		if(optCategory.isPresent()) {
			CategoryEntity entity optCategory.get();

			BeanUtils.copyProperties (entity, cateModel);
			cateModel.setIsEdit(true);

			model.addAttribute("category", cateModel);
			return new ModelAndView("admin/categories/addOrEdit", model);
			model.addAttribute("message", "Category is not existed!!!!"); return new ModelAndView("forward:/admin/categories", model);
		}*/

	}
}