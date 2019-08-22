package com.cjw.forum.cache;

import com.cjw.forum.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @param <T>
 * @Author: chenjianwei
 * @Description:
 * @Date: Created in 18:50 2019-08-21
 */
public class TagCache {
    public static List<TagDto> get() {
        List<TagDto> tagDTOS = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("javascript", "php", "css", "html", "html5", "java", "node.js", "python", "c++", "c", "golang", "objective-c", "typescript", "shell", "swift", "c#", "sass", "ruby", "bash", "less", "asp.net", "lua", "scala", "coffeescript", "actionscript", "rust", "erlang", "perl"));
        tagDTOS.add(program);

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDTOS.add(framework);


        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "ubuntu", "centos", "缓存 tomcat", "负载均衡", "unix", "hadoop", "windows-server"));
        tagDTOS.add(server);

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "mongodb", "sql", "oracle", "nosql memcached", "sqlserver", "postgresql", "sqlite"));
        tagDTOS.add(db);

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "visual-studio-code", "vim", "sublime-text", "xcode intellij-idea", "eclipse", "maven", "ide", "svn", "visual-studio", "atom emacs", "textmate", "hg"));
        tagDTOS.add(tool);
        return tagDTOS;
    }

    public  static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        // List<List<String>> collect = tagDtos.stream().flatMap(tagDto -> Stream.of(tagDto.getTags())).collect(Collectors.toList());
        List<String> tagList = tagDtos.stream().flatMap(tagDto -> tagDto.getTags().stream()).collect(Collectors.toList());
        // List<String> invalidList = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.toList());
        String invalidStr = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        // if(invalidList.size() !=0) {
        //     return  false;
        // }
        return invalidStr;
    }
}
