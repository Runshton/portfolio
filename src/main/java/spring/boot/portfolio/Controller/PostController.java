package spring.boot.portfolio.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.boot.portfolio.Model.CategoryModel.LangCollection;
import spring.boot.portfolio.Model.CategoryModel.PostModel.ContentMode;
import spring.boot.portfolio.Model.CategoryModel.PostModel.PostCollection;
import spring.boot.portfolio.Model.CategoryModel.PostModel.PostContent;
import spring.boot.portfolio.Model.CategoryModel.SkillCollection;
import spring.boot.portfolio.Service.PostService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller @RequestMapping("/Post")
public class PostController {
    @Autowired
    PostService postService;

//    @Value("${app.password}")
//    String password;

    @RequestMapping("/PostList") /*@ResponseBody*/
    public String PrintList(Model model, String mode, String value){
        if (mode == null) mode = "all";
        List<PostCollection> postCollections;
        switch (mode) {
            case "include_name" -> postCollections = postService.findByNameInclude(value);
            case "name" -> {
                postCollections = new ArrayList<>();
                postCollections.add(postService.findByName(value));
            }
            case "lang" -> postCollections = postService.findByLang(value);
            case "skill" -> postCollections = postService.findBySkill(value);
            case "id" -> {
                postCollections = new ArrayList<>();
                postCollections.add(postService.findById(value));
            }
            default -> postCollections = postService.findAll();
        }

        model.addAttribute("posts", postCollections);
        return "Post/PostList";
    }
    @RequestMapping("/PostInsertPage")
    public String PostInsertPage(Model model){
        List<String> LangNames = postService.findLangAll().stream().map(
                LangCollection::getName).toList();
        List<String> SkillNames = postService.findSkillAll().stream().map(
                SkillCollection::getName).toList();
        model.addAttribute("LangNames", LangNames);
        model.addAttribute("SkillNames", SkillNames);
        //카테고리 이름 모음을 만들어서, 게시글 작성 시 존재하는 카테고리에 데이터를 추가할지 새로 만들지 선택 가능하게
        return "Post/PostInsert";
    }
    @RequestMapping("/PostInsertAction")
    public String PostInsertAction(String post_name,
                                   @RequestParam(name = "post_type")List<String> post_type,
                                   @RequestParam(name = "post_content")List<String> post_content,
                                   @RequestParam(name = "post_lang")List<String> post_category,
                                   @RequestParam(name = "post_skill")List<String> post_skill){

        if(post_category.isEmpty() || post_skill.isEmpty()){
            return "redirect:PostInsertPage";
        }
        AtomicInteger count = new AtomicInteger();
        List<PostContent> postContents = post_type.stream().map((t) -> {
            PostContent p = new PostContent();
            ContentMode m = ContentMode.str;
            switch(t){
                case "Text":
                    m = ContentMode.str;
                    break;
                case "Img":
                    m = ContentMode.img;
                    break;
                case "Link":
                    m = ContentMode.link;
                    break;
            }
            p.setMode(m);
            p.setContent(post_content.get(count.get()));
            count.addAndGet(1);
            return p;
        }).toList();

//        System.out.println(post_category);
//        System.out.println(post_skill);
        postService.postSave(new PostCollection(post_name, postContents, post_category, post_skill));
//        postService.CategoryInputPostId(postService.postSave(temp).getId(),post_category);
        return "redirect:PostInsertPage";
    }

    @RequestMapping("/AddLang")
    public String AddLang(String name){
        postService.saveLang(name);
        return "redirect:PostInsertPage";
    }
    @RequestMapping("/AddSkill")
    public String AddSkill(String name, String description, int level){
        postService.saveSkill(name, description, level);
        return "redirect:PostInsertPage";
    }
//    @RequestMapping("/Password")
//    public String Password(){
//        System.out.println(password);
//        return "redirect:/PostInsertPage";
//    }
}
