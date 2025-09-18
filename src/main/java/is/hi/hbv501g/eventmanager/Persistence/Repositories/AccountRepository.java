package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Account;
import org.springframework.data.repository.Repository;

/**
 * Interface for user account repository actions
 */
public interface AccountRepository extends Repository<Account, Long> {

}