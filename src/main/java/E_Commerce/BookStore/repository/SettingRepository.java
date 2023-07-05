package E_Commerce.BookStore.repository;

import E_Commerce.BookStore.domain.Setting;
import E_Commerce.BookStore.domain.SettingCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingRepository extends JpaRepository<Setting, String> {

    public List<Setting> findByCategory(SettingCategory category);
}
