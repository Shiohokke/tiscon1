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
public class DetailController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;

    @RequestMapping(value = {"/detail", "/my/detail"}, method = RequestMethod.GET)
    @Cacheable("model")
    public String detail(@RequestParam("genreId") String genreId, @RequestParam("subgenreId") String subgenreId, @RequestParam("itemId") String itemId, Model model) {
        model.addAttribute("genreId", genreId);
        model.addAttribute("subgenreId", subgenreId);
        model.addAttribute("genreName", genreRepository.getGenreName(genreId));
        model.addAttribute("subgenreName", genreRepository.getSubGenreName(genreId, subgenreId));

        try {
            model.addAttribute("item", categoryRepository.searchItem(genreId, itemId));
        } catch (Exception e) {
            throw new SystemException();
        }

        return "detail";
    }
}