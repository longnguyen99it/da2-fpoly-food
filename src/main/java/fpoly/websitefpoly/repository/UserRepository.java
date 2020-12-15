package fpoly.websitefpoly.repository;


import fpoly.websitefpoly.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Query("select users from " +
            "Users users inner join Invoice inv on users.id = inv.users.id " +
            "where inv.status = ?1 group by users.id order by sum(inv.amountTotal) desc ")
    Page<Users> topInvoice(String status, Pageable pageable);
}
