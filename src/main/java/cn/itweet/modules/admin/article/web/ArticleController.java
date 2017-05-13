package cn.itweet.modules.admin.article.web;

import cn.itweet.common.config.ItweetProperties;
import cn.itweet.common.exception.SystemException;
import cn.itweet.common.utils.PageUtils;
import cn.itweet.common.utils.TimeMillisUtils;
import cn.itweet.modules.admin.article.entity.Article;
import cn.itweet.modules.admin.article.entity.Categories;
import cn.itweet.modules.admin.article.service.article.ArticleService;
import cn.itweet.modules.admin.article.service.categories.CategoriesService;
import cn.itweet.modules.admin.article.service.tag.TagService;
import cn.itweet.modules.admin.article.utils.ArticleDto;
import cn.itweet.modules.admin.document.entiry.Document;
import cn.itweet.modules.admin.document.service.StorageService;
import cn.itweet.modules.admin.user.entity.SysUser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by whoami on 17/04/2017.
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private TagService tagService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ItweetProperties itweetProperties;


    /**
     *文章列表
     * @param page
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Model model) {
        if(page !=0) page = page -1;

        Page<ArticleDto> articleList = articleService.list(page);
        model.addAttribute("articleList",articleList);

        PageUtils pageUtils = new PageUtils("/admin/article/list?",page,articleList.getTotalPages(),articleList.getTotalElements(),pageSize);
        model.addAttribute("pb",pageUtils);

        return "admin/article/a_list";
    }

    /**
     * 文章按Title查询
     * @param page
     * @param model
     * @return
     */
    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public String select(@RequestParam(value = "title") String title, @RequestParam(value = "page", defaultValue = "0") Integer page,Model model) {
        if(page !=0) page = page -1;

        Page<ArticleDto> articleList = articleService.searchByTitle(page,title);

        model.addAttribute("articleList",articleList);

        PageUtils pageUtils = new PageUtils("/admin/article/select?title="+ title+"&",page,articleList.getTotalPages(),articleList.getTotalElements(),itweetProperties.getPagSize());
        model.addAttribute("pb",pageUtils);

        model.addAttribute("title",title);
        return "admin/article/a_list";
    }

    /**
     * 文章添加页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Model model) {
        List<Categories> categoriesList =  categoriesService.list();
        model.addAttribute("categoriesList",categoriesList);
        return "admin/article/a_add";
    }

    /**
     * 文章添加
     * @param model
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String add(@RequestParam String tagNames,@RequestParam(value = "categoriesId") Integer categoriesId,Model model,Article article) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            article.setAuthor(userDetails.getUsername());
            article.setState(0);
            articleService.addArticle(article,tagNames,categoriesId);
        } catch (SystemException e) {
            model.addAttribute("form",article);
            model.addAttribute("message","<script>toastr.error(\"" + e.getMessage() + "\")</script>");
            return "admin/article/a_add";
        }
        return "redirect:/admin/article/list";
    }

    /**
     * 文章修改页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public String edit(Model model,@PathVariable Integer id) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("form",article);

        List<Categories> categoriesList =  categoriesService.list();
        model.addAttribute("categoriesList",categoriesList);

        Integer categoriesId = categoriesService.findCategoriesIdByArticleId(id);
        model.addAttribute("categoriesId",categoriesId);

        String tagNames = tagService.findTagNamesByArticleId(id);
        model.addAttribute("tagNames",tagNames);

        return "admin/article/a_edit";
    }

    /**
     * 文章修改
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    public String edit(@RequestParam  String tagNames,@RequestParam(value = "categoriesId") Integer categoriesId,Model model,Article article) {
        try {
            articleService.update(article,tagNames,categoriesId);
        } catch (SystemException e) {
            model.addAttribute("form",article);
            model.addAttribute("message","<script>toastr.error(\"" + e.getMessage() + "\")</script>");
            return "admin/article/a_edit";
        }
        return "redirect:/admin/article/list";
    }

    /**
     * 文章按ID删除
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public String delete(Model model,@PathVariable Integer id) {
        articleService.deleteById(id);
        return "redirect:/admin/article/list";
    }

    /**
     * 编辑文章内容页面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/addContent/{id}",method = RequestMethod.GET)
    public String addContent(Model model,@PathVariable Integer id) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("form",article);
        return "admin/article/article";
    }

    /**
     * 编辑文章内容
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/addContent/{id}",method = RequestMethod.POST)
    public String addContent(@RequestParam(value = "content") String content,@RequestParam(value = "htmlContent") String htmlContent,Model model,@PathVariable Integer id) {
        Article article = articleService.getArticleById(id);
        article.setHtmlContent(htmlContent);
        article.setContent(content);
        article.setUpdateDate(new Date());
        try {
            articleService.update(article);
        } catch (SystemException e) {
            model.addAttribute("form",article);
            model.addAttribute("message","<script>toastr.error(\"" + "正文编辑失败" + "\")</script>");
            return "admin/article/article";
        }
        return "redirect:/admin/article/list";
    }

    /**
     * 文章按ID预览
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public String view(Model model,@PathVariable Integer id) {
        Article article = articleService.getArticleById(id);
        model.addAttribute("form",article);
        return "admin/article/view";
    }

    /**
     * 文章发布
     * @param model
     * @return
     */
    @RequestMapping(value = "/release/{id}",method = RequestMethod.GET)
    public String release(@PathVariable Integer id,Model model) {
        Article article = articleService.getArticleById(id);
        article.setState(1);
        try {
            articleService.update(article);
        } catch (SystemException e) {
            model.addAttribute("message","<script>toastr.error(\"" + "文章发布失败" + "\")</script>");
        }
        return "redirect:/admin/article/list";
    }

    /**
     * 取消文章发布
     * @param model
     * @return
     */
    @RequestMapping(value = "/cancelRelease/{id}",method = RequestMethod.GET)
    public String cancelRelease(@PathVariable Integer id,Model model) {
        Article article = articleService.getArticleById(id);
        article.setState(0);
        try {
            articleService.update(article);
        } catch (SystemException e) {
            model.addAttribute("message","<script>toastr.error(\"" + "文章取消发布失败" + "\")</script>");
        }
        return "redirect:/admin/article/list";
    }


    /**
     * 文章图片上传
     * @param request
     * @param response
     * @param attach
     */
    @RequestMapping(value="/uploadfile",method=RequestMethod.POST)
    public void uploadfile(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach){

        try {
            request.setCharacterEncoding( "utf-8" );
            response.setHeader( "Content-Type" , "text/html" );
            String rootPath = request.getSession().getServletContext().getRealPath("/")+itweetProperties.getUploadSuffix();
            String ruleFilename = storageService.store(attach,rootPath);

            response.getWriter().write( "{\"success\": 1, \"message\":\"上传成功\",\"url\":\"/" + itweetProperties.getUploadSuffix() + "/" + ruleFilename + "\"}" );
        } catch (Exception e) {
            try {
                response.getWriter().write( "{\"success\":0}" );
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

}
