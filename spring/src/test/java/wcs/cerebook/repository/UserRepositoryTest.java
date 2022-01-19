package wcs.cerebook.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
/*
    @Test
    public void essai() {
        assertEquals(1, 1);
    }

    @Test
    public void findByNickName() {
        assertNull(userRepository.findByNickName("Darth Vador"));
        assertEquals("Wolverine",
                userRepository.findByNickName("Wolverine").getUsername());
    }

    @Test
    public void save() {
        assertEquals(1, userRepository.count());
        assertThat(userRepository.findByNickName("Cyclope")).isNull();

        userRepository.save(new CerebookUser("Cyclope", "Michel", "Mipoivre", "Paris", "rue des goelans", "roger@gmail.com", "12345", new Date(21,2,2021)));

        assertThat(userRepository.findByNickName("Cyclope")).isNotNull();
        assertEquals("Cyclope",
                userRepository.findByNickName("Cyclope").getUsername());
    }

 */
}