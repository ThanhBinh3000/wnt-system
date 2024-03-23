package vn.com.gsoft.system.repository;

import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vn.com.gsoft.system.entity.NhaThuocs;
import vn.com.gsoft.system.entity.UserProfile;
import vn.com.gsoft.system.model.system.NhaThuocsReq;
import vn.com.gsoft.system.model.system.UserProfileReq;

import java.util.List;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
//    List<NhaThuocs> findByUserId(Long id);

    @Query("SELECT c FROM UserProfile c " +
            " WHERE 1=1 " +
            "ORDER BY c.userId desc"
    )
    Page<UserProfile> searchPage(@Param("param") UserProfileReq param, Pageable pageable);

    @Query("SELECT c FROM UserProfile c " +
            " WHERE 1=1 " +
            "ORDER BY c.userId desc"
    )
    List<UserProfile> searchList(@Param("param") UserProfileReq param);

    List<UserProfile> findAll();
}
