package de.ingrid.iplug.excel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import de.ingrid.iplug.excel.model.Column;
import de.ingrid.iplug.excel.model.Filter;
import de.ingrid.iplug.excel.model.Row;
import de.ingrid.iplug.excel.model.Sheets;
import de.ingrid.iplug.excel.model.Filter.FilterType;
import de.ingrid.iplug.excel.service.SheetService;

@Controller
@SessionAttributes("sheets")
public class AddFilterController {
	
	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.GET)
	public String addFilter(@ModelAttribute("sheets") Sheets sheets, @RequestParam(required=true) final String type,
			@RequestParam(required=true) final int index, @RequestParam(required=true) final String label, ModelMap model) {
		model.addAttribute("type", type);
		model.addAttribute("index", index);
		model.addAttribute("label", label);
		model.addAttribute("filterTypes", FilterType.values());
		return "/iplug/addFilter";
	}
	
	@RequestMapping(value = "/iplug/addFilter.html", method = RequestMethod.POST)
	public String addFilterPost(@ModelAttribute("sheets") Sheets sheets, @RequestParam(required=true) final String type,
			@RequestParam(required=true) final int index, @RequestParam(required=true) final String filterType,
			@RequestParam(required=true) final String expression) {
		
		Filter filter = new Filter(expression, FilterType.valueOf(filterType));
		if(type.equals("col")){
			Column column = SheetService.getColumnByIndex(sheets.getSheets().get(0), index);
			column.addFilter(filter);
		}else if(type.equals("row")){
			Row row = SheetService.getRowByIndex(sheets.getSheets().get(0), index);
			row.addFilter(filter);
		}
		
		return "redirect:/iplug/mapping.html";
	}
	
	@RequestMapping(value = "/iplug/removeFilter.html", method = RequestMethod.GET)
	public String removeFilter(@ModelAttribute("sheets") Sheets sheets, @RequestParam(required=true) final String type,
			@RequestParam(required=true) final int index, @RequestParam(required=true) final int filterIndex){
		
		if(type.equals("col")){
			Column column = SheetService.getColumnByIndex(sheets.getSheets().get(0), index);
			Filter filter = column.getFilters().get(filterIndex);
			column.removeFilter(filter);
		}else if(type.equals("row")){
			Row row = SheetService.getRowByIndex(sheets.getSheets().get(0), index);
			Filter filter = row.getFilters().get(filterIndex);
			row.removeFilter(filter);
		}
		
		return "redirect:/iplug/mapping.html";
	}
}
