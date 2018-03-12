package com.hzwl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Region;
import com.hzwl.utils.PinYin4jUtils;

/**
 * 区域管理
 * 
 * 
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	// 接收上传的文件
	private File myFile;
	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	// 模糊查询条件
	private String q;

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	// 添加
	public String saveRegion() {

		System.out.println(model);
		regionService.saveRegion(model);

		return "list";
	}

	// 删除
	public String deleteRegion() {

		System.out.println(ids + "------------------------");
		regionService.deleteMany(ids);

		return "list";
	}

	// 编辑
	public String updateRegion() {

		regionService.updataRegion(model);

		return "list";
	}

	/**
	 * 分页查询
	 * 
	 * @throws IOException
	 */
	public String pageQuery() throws IOException {

		// 在查询之前，封装条件
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		String queryprovince = model.getPostcode();
		String querycity = model.getCity();
		String querydistrict = model.getDistrict();
		String querypostcode = model.getPostcode();

		// 进行模糊查询
		if (StringUtils.isNotBlank(queryprovince)) {
			detachedCriteria2.add(Restrictions.like("province", "%" + queryprovince
					+ "%"));
		}
		if (StringUtils.isNotBlank(querycity)) {
			detachedCriteria2.add(Restrictions.like("city", "%"
					+ querycity + "%"));
		}
		if (StringUtils.isNotBlank(querydistrict)) {
			detachedCriteria2.add(Restrictions.like("district", "%"
					+ querydistrict + "%"));
		}
		if (StringUtils.isNotBlank(querypostcode)) {
			detachedCriteria2.add(Restrictions.like("postcode", "%"
					+ querypostcode + "%"));
		}

		regionService.pageQuery(pageBean);
		
		this.writePageBean2Json(pageBean, new String[] { "currentPage",
				"detachedCriteria", "pageSize", "subareas" });
		return NONE;
	}
	
	
	/**
	 * 查询所有的区域数据，返回json
	 * 
	 * @throws IOException
	 */
	public String listajax() throws IOException {

		
		List<Region> list = null;// regionService.findAll();
		if (StringUtils.isNotBlank(q)) {
			list = regionService.findByQquery(q);
		} else {
			list = regionService.findAll();
		}
		String[] excludes = new String[] { "subareas" };
		this.writeList2Json(list, excludes);
		return NONE;
	}

	/**
	 * 批量导入
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String importXls() throws Exception {
		String flag = "1";
		// 使用ＰＯＩ解析Ｅｘｃｅｌ文件
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(
					new FileInputStream(myFile));
			// 获得第一个sheet页
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<Region> list = new ArrayList<Region>();
			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if (rowNum == 0) {
					// 第一行，标题行，忽略
					continue;
				}
				String id = row.getCell(0).getStringCellValue();

				String province = row.getCell(1).getStringCellValue();

				String city = row.getCell(2).getStringCellValue();

				String district = row.getCell(3).getStringCellValue();

				String postcode = row.getCell(4).getStringCellValue();

				Region region = new Region(id, province, city, district,
						postcode, null);

				list.add(region);
			}
			regionService.saveBatch(list);
		} catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType(
				"text/html;charset=UTF-8");
		ServletActionContext.getResponse().getWriter().print(flag);
		return NONE;
	}

	

}
