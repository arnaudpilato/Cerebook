package wcs.cerebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import wcs.cerebook.entity.CerebookMovie;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.repository.MovieRepository;
import wcs.cerebook.repository.UserRepository;
import wcs.cerebook.services.MediaService;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
public class MovieController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MediaService mediaService;

    @GetMapping("/movie")
    public String gettAllMovie(Model model, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));
        model.addAttribute("movies", movieRepository.findAll());

        return "cerebookMovie/movie";
    }

    @GetMapping("/movie/show")
    public String showMovie(Model model, @RequestParam Long id, Principal principal) {
        // PIL : Récupération de l'user pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        model.addAttribute("movie", movieRepository.findById(id));

        return "cerebookVideo/movie_show";
    }

    @GetMapping("/movie/update")
    public String updateMovie(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "cerebookVideo/movie_update";
    }

    @PostMapping("/movie/update")
    public String postMovieUpdate(@ModelAttribute CerebookMovie cerebookMovie, @RequestParam(value = "file_movie") MultipartFile movie, Principal principal, HttpServletRequest httpServletRequest) throws IOException {
        CerebookUser user = userRepository.getCerebookUserByUsername(principal.getName());

        if (!movie.isEmpty()) {
            String filename = "static/css/data/" + movie.getOriginalFilename();
            Files.copy(movie.getInputStream(), Paths.get("src/main/resources/public/" + filename), StandardCopyOption.REPLACE_EXISTING);
            cerebookMovie.setMoviePath(filename);

            try {
                mediaService.uploadMovie(
                        filename,
                        movie.getInputStream(),
                        movie.getSize(),
                        user
                );
            } catch (IOException e) {
                //redirectAttributes.addAttribute("errorMessage", e.getMessage());
            }

        }

        user.getUserMovies().clear();

        for (String entry : httpServletRequest.getParameterMap().keySet()) {
            try {
              CerebookMovie checkedMovie =  movieRepository.getById(Long.parseLong(entry));
              user.getUserMovies().add(checkedMovie);
            } catch (Exception e) {
                // Pil : Aucune valeur à catcher
            }
        }

        user = userRepository.saveAndFlush(user);

        return "redirect:/movie";
    }

    @GetMapping("/movie/delete")
    public String deleteVideo(@RequestParam Long id) {
        movieRepository.deleteById(id);

        return "redirect:/movie";
    }
}
