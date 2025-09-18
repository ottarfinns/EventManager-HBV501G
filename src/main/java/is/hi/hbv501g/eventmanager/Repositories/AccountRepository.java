package is.hi.hbv501g.eventmanager.Repositories;

import is.hi.hbv501g.eventmanager.Entities.Account;
import org.springframework.data.repository.Repository;

/**
 * Interface for user account repository actions
 */
public interface AccountRepository extends Repository<Account, Long> {

}