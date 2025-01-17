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

import ch.qos.logback.core.model.Model;

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

		/*
		 * @GetMapping("edit/{categoryId)") public ModelAndView edit (ModelMap
		 * model, @PathVariable("categoryId") Long categoryId) {
		 * Optional<CategoryEntity> optCategory categoryService.findById(categoryId);
		 * Category Model cateHodel = new CategoryModel();
		 * 
		 * if(optCategory.isPresent()) { CategoryEntity entity optCategory.get();
		 * 
		 * BeanUtils.copyProperties (entity, cateModel); cateModel.setIsEdit(true);
		 * 
		 * model.addAttribute("category", cateModel); return new
		 * ModelAndView("admin/categories/addOrEdit", model);
		 * model.addAttribute("message", "Category is not existed!!!!"); return new
		 * ModelAndView("forward:/admin/categories", model); }
		 */

	}

	@GetMapping("/add")
	public String add(ModelMap model) {

		return "admin/add";

	}

	@PostMapping("save")
	public String add(@Valid CategoryEntity category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "admin/categories/add";
		}
		categoryService.save(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") long id, ModelMap model) {
		CategoryEntity category = categoryService.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		model.addAttribute("category", category);
		return "admin/edit";
	}

	@PostMapping("/update")
	public String update(@Valid CategoryEntity category, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "admin/home";
		}
		categoryService.save(category);
		return "redirect:/admin/categories";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id, Model model) {
		categoryService.deleteById(id);
		return "redirect:/admin/categories";
	}

	@RequestMapping("searchpaginated")
	  public String search (ModelMap model,@RequestParam(name="name", required = false) String name,@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
	  int count = (int) categoryService.count();
	  int currentPage= page.orElse(1);
	  int pageSize= size.orElse(3);
	  Pageable pageable= PageRequest.of (currentPage-1, pageSize, Sort.by("name"));
	  Page<CategoryEntity> resultPage= null;
	  if(StringUtils.hasText(name)) { 
	resultPage = categoryService.findByNameContaining(name, pageable);
	  model.addAttribute("name", name);}
	  else { 
		  resultPage = categoryService.findAll(pageable);
	  }
	  int totalPages = resultPage.getTotalPages();
	  if(totalPages > 0) {
	  int start= Math.max(1, currentPage-2);
	  int end = Math.min(currentPage + 2, totalPages);
	  if(totalPages > count) {
		  
		  if(end == totalPages) 
			  start =end+ count;
		  else if (start == 1) 
			  end =start + count;
	  }
	  List<Integer> pageNumbers = IntStream.rangeClosed(start, end) 
			  .boxed().collect(Collectors.toList()); 
	  	model.addAttribute("pageNumbers", pageNumbers); 
	  	model.addAttribute("categoryPage", resultPage); 
	  	
	  }
	  return "admin/search";
	  }

}

