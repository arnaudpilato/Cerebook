package wcs.cerebook.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import wcs.cerebook.entity.CerebookUser;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void essai() {
        assertEquals(1, 1);
    }

    @Test
    public void findByName() {
        assertNull(userRepository.findByName("Darth Vador"));
        assertEquals("Wolverine",
                userRepository.findByName("Wolverine").getName());
    }

    @Test
    public void save() {
        assertEquals(1, userRepository.count());
        assertThat(userRepository.findByName("Cyclope")).isNull();

        userRepository.save(new CerebookUser(
                "Cyclope"));

        assertThat(userRepository.findByName("Cyclope")).isNotNull();
        assertEquals("Cyclope",
                userRepository.findByName("Cyclope").getName());
    }
}