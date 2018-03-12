package com.hzwl.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hzwl.controller.base.BaseAction;
import com.hzwl.entity.Decidedzone;
import com.hzwl.entity.Region;
import com.hzwl.entity.Subarea;

/**
 * 分区管理
 * 
 * 
 * 
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {

	private String ids;
	private String did;
	private String decidedzoneId;
	
	public String getDecidedzoneId() {
		return decidedzoneId;
	}

	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	
	//添加
	public String add() {
		System.out.println("添加分区："+model);
		subareaService.save(model);
		return "list";
	}

	//更新
	public String updataSubarea() {

		subareaService.editSubarea(model);
		return "list";
	}

	//删除
	public String deleteSubarea() {
		subareaService.deleteSubarea(ids);
		return "list";
	}

	//分页查询
	public String pageQuery() throws Exception {

		
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();

		String addresskey = model.getAddresskey();

		Region region = model.getRegion();

		if (StringUtils.isNotBlank(addresskey)) {
			// 按照地址关键字模糊查询
			detachedCriteria2.add(Restrictions.like("addressKey", "%"+addresskey+"%"));
		}

		if (region != null) {
			// 创建别名，用于多表关联查询
			detachedCriteria2.createAlias("region", "r");
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();

			if (StringUtils.isNotBlank(province)) {
				// 按照省进行模糊查询
				detachedCriteria2.add(Restrictions.like("r.province", "%"
						+ province + "%"));
			}
			if (StringUtils.isNotBlank(city)) {
				// 按照省进行模糊查询
				detachedCriteria2.add(Restrictions.like("r.city", "%" + city
						+ "%"));
			}
			if (StringUtils.isNotBlank(district)) {
				// 按照省进行模糊查询
				detachedCriteria2.add(Restrictions.like("r.district", "%"
						+ district + "%"));
			}
		}
		subareaService.pageQuery(pageBean);
		
		//去除不要的属性
		String[] excludes = new String[] { "detachedCriteria", "currentPage",
				"pageSize", "decidedzone", "subareas" };
		this.writePageBean2Json(pageBean, excludes);

		return NONE;
	}
	
	public String listSubarea() throws IOException{
		
		List<Subarea> list=subareaService.findNotAssociation();
		String[] excludes = new String[]{"decidedzone","region"};
		this.writeList2Json(list, excludes );
		return null;
	}
	
	

	/**
	 * 查询已经关联定区的分区
	 * @return
	 * @throws IOException
	 */
	public String assoctionDecidedzone() throws IOException{
		
		
		List<Decidedzone> listDecidedzone = decidedzonService.findDecidedzoneById(decidedzoneId);
		
		DetachedCriteria detachedCriteria2 = pageBean.getDetachedCriteria();
		
		detachedCriteria2.createAlias("decidedzone", "s");
		
		String did=listDecidedzone.get(0).getId();
		
		if (StringUtils.isNotBlank(did)) {
			detachedCriteria2.add(Restrictions.like("s.id", did));
		}
		
		subareaService.pageQuery(pageBean);
		
		//去除不要的属性
		String[] excludes = new String[] { "detachedCriteria", "currentPage",
				"pageSize", "decidedzone", "subareas" };
		this.writePageBean2Json(pageBean, excludes);
		return null;
	}

	/**
	 * 使用POI写入Excel文件，提供下载
	 * 
	 * @throws IOException
	 */
	public String exportXls() throws IOException {

		List<Subarea> list = subareaService.findAll();
		// 在内存中创建一个Excel文件，通过输出流写到客户端提供下载
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个sheet页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		// 创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("区域编号");
		headRow.createCell(2).setCellValue("地址关键字");
		headRow.createCell(3).setCellValue("省市区");

		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getRegion().getId());
			dataRow.createCell(2).setCellValue(subarea.getAddresskey());
			Region region = subarea.getRegion();
			dataRow.createCell(3).setCellValue(
					region.getProvince() + region.getCity()
							+ region.getDistrict());
		}

		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest()
				.getHeader("User-Agent");
		// filename = FileUtils.encodeDownloadFilename(filename, agent);
		// 一个流两个头
		ServletOutputStream out = ServletActionContext.getResponse()
				.getOutputStream();
		String contentType = ServletActionContext.getServletContext()
				.getMimeType(filename);
		ServletActionContext.getResponse().setContentType(contentType);
		ServletActionContext.getResponse().setHeader("content-disposition",
				"attchment;filename=" + filename);
		workbook.write(out);
		return NONE;
	}
}
