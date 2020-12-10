package fpoly.websitefpoly.repository;


import fpoly.websitefpoly.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("select user from " +
            "User user inner join Invoice inv on user.id = inv.user.id " +
            "where inv.status = ?1 group by user.id order by sum(inv.amountTotal) desc ")
    Page<User> topInvoice(String status,Pageable pageable);
}
