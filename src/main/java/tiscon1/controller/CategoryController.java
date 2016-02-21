package tiscon1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tiscon1.exception.SystemException;
import tiscon1.repository.CategoryRepository;
import tiscon1.repository.GenreRepository;

/**
 * @author fujiwara
 */
@Controller
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;

    @RequestMapping(value = {"/category", "/my/category"}, method = RequestMethod.GET)
    @Cacheable("model")
    public String category(@RequestParam("genreId") String genreId, @RequestParam("subgenreId") String subgenreId, Model model) {
        model.addAttribute("genreId", genreId);
        model.addAttribute("subgenreId", subgenreId);
        model.addAttribute("genreName", genreRepository.getGenreName(genreId));
        model.addAttribute("subgenreName", genreRepository.getSubGenreName(genreId, subgenreId));

        try {
            model.addAttribute("ranking", categoryRepository.findTop10(genreId, subgenreId));
        } catch (Exception e) {
            throw new SystemException();
        }
        return "category";
    }
}