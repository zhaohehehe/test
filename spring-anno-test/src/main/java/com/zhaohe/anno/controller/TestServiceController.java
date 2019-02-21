package com.zhaohe.anno.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/service")
/**
 * <p>
 * 若希望在多个请求之间共用数据，则可以在控制器类上标注一个 @SessionAttributes,配置需要在session中存放的数据范围，Spring
 * MVC将存放在model中对应的数据暂存到HttpSession 中。
 * </p>
 * <p>
 * @SessionAttributes只能使用在类定义上。
 * </p>
 * <p>
 * @SessionAttributes 除了可以通过属性名指定需要放到会 话中的属性外，还可以通过模型属性的对象类型指定哪些模型属性需要放到会话中 例如：
 * @SessionAttributes(types=User.class)会将model中所有类型为 User的属性添加到会话中。
 * @SessionAttributes(value={“user1”, “user2”})会将model中属性名为user1和user2的属性添加到会话中。
 * @SessionAttributes(types={User.class, Dept.class}) 会将model中所有类型为User和Dept的属性添加到会话中。
 * @SessionAttributes(value={“user1”,“user2”},types={Dept.class})会将model中属性名为user1和user2以及类型为Dept的属性添加到会话中。</p>
 * <p>value和type之间是并集关系</p>
 */
@SessionAttributes(value = { "attr1", "attr2" }, types = { Integer.class })
public class TestServiceController {
	
	@RequestMapping("/testSessionAttributes") 
	public String testSessionAttributes(Model model) {
		model.addAttribute("attr1", 100);
		model.addAttribute("attr2", 200);
		return "success";
	}

	@RequestMapping(value = "/application/form", produces = { "application/x-www-form-urlencoded",
			"multipart/form-data" }, method = { RequestMethod.POST })
	public @ResponseBody String getApplicationForm(@RequestParam("name") String name, @RequestParam("age") String age) {
		System.out.println("name=" + name + "&" + "age=" + age);
		return "name=" + name + "&" + "age=" + age;
	}

	/**
	 * 需要在web.xml和dispatcher.xml文件中添加相关配置
	 * 
	 * @param file
	 * @throws IOException
	 */
	@RequestMapping(value = "/multipart/file", produces = "multipart/form-data", method = RequestMethod.POST)
	public @ResponseBody String getFile(@RequestParam("file") MultipartFile[] files, HttpServletRequest request)
			throws IOException {
		System.out.println("=====================================");
		if (null != files && files.length > 0) {
			// 遍历并保存文件
			for (MultipartFile file : files) {
				System.out.println("源文件名称：" + file.getOriginalFilename());
				InputStream in = file.getInputStream();
				byte[] b = new byte[50];
				while (in.read(b, 0, b.length) != -1) {
					System.out.println("------------");
					System.out.println(new String(b));
				}
				file.transferTo(new File(file.getOriginalFilename()));
				in.close();
			}
		}
		System.out.println("@SessionAttributes" + request.getSession().getAttribute("attr1"));
		System.out.println("@SessionAttributes" + request.getSession().getAttribute("attr2"));
		return "success";
	}
}
