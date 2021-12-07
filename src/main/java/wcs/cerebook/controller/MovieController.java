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
import wcs.cerebook.repository.MovieRepository;
import wcs.cerebook.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

@Controller
public class MovieController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movie")
    public String gettAllMovie(Model model, Principal principal) {
        // PIL : Récupération de l'user principal pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "/cerebookMovie/movie";
    }

    @GetMapping("/movie/show")
    public String showMovie(Model model, @RequestParam Long id, Principal principal) {
        // PIL : Récupération de l'user pour la navbar
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        model.addAttribute("movie", movieRepository.findById(id));

        return "/cerebookVideo/movie_show";
    }

    @GetMapping("/movie/update")
    public String updateMovie(Model model, Principal principal) {
        model.addAttribute("user", userRepository.findByUsername(principal.getName()));

        return "/cerebookVideo/movie_update";
    }

    @PostMapping("/movie/update")
    public String postMovieUpdate(@ModelAttribute CerebookMovie cerebookMovie, @RequestParam(value = "file_movie") MultipartFile movie, Principal principal, HttpServletRequest httpServletRequest) throws IOException {
        if (!movie.isEmpty()) {
            Files.copy(movie.getInputStream(), Paths.get("src/main/resources/public/static/css/data/" + movie.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            cerebookMovie.setMoviePath("/static/css/data/" + movie.getOriginalFilename());
            if (cerebookMovie.getId() == null) {
                cerebookMovie.setUser(userRepository.findByUsername(principal.getName()));
            }

            movieRepository.save(cerebookMovie);
        }

        List<CerebookMovie> coverCerebookMovies = movieRepository.findAll();
        for (CerebookMovie coverMovie : coverCerebookMovies) {
            coverMovie.setActor(false);
            movieRepository.save(coverMovie);
        }

        for (String entry : httpServletRequest.getParameterMap().keySet()) {
            try {
                movieRepository.getById(Long.parseLong(entry)).setActor(true);
                movieRepository.save(movieRepository.getById(Long.parseLong(entry)));
            } catch (Exception e) {
                // Pil : Aucune valeur à catcher
            }
        }

        return "redirect:/movie";
    }

    @GetMapping("/movie/delete")
    public String deleteVideo(@RequestParam Long id) {
        movieRepository.deleteById(id);

        return "redirect:/movie";
    }
}
