package is.hi.hbv501g.eventmanager.Persistence.Repositories;

import is.hi.hbv501g.eventmanager.Persistence.Entities.Event;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EventSpecs {
    /** If name== null than spec is ignored **/
    public static Specification<Event> hasName(String name) {
        return ((root, query, criteriaBuilder) ->
            name == null ?
                    criteriaBuilder.conjunction() :
                    criteriaBuilder.equal(root.get("name"), name)
        );
    }

    /** If type== null than spec is ignored **/
    public static Specification<Event> isType(Event.eventType type) {
        return ((root, query, criteriaBuilder) ->
                type == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("type"), type.toString())
        );
    }

    /** If venueId== null than spec is ignored **/
    public static Specification<Event> hasVenueId(Long venueId) {
        return ((root, query, criteriaBuilder) ->
                venueId == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("venue_id"), venueId)
        );
    }

    /** If dateFrom == null than spec is ignored **/
    public static Specification<Event> dateAfter(LocalDateTime dateFrom) {
        return ((root, query, criteriaBuilder) ->
                dateFrom == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThan(root.get("date_time"), dateFrom.toString())
        );
    }

    /** If dateTo == null than spec is ignored **/
    public static Specification<Event> dateBefore(LocalDateTime dateTo) {
        return ((root, query, criteriaBuilder) ->
                dateTo == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThan(root.get("date_time"), dateTo.toString())
        );
    }

    /** If maxOccupancy== null than spec is ignored **/
    public static Specification<Event> hasMaxOccupancy(Integer maxOccupancy) {
        return ((root, query, criteriaBuilder) ->
                maxOccupancy == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThanOrEqualTo(root.get("max_people"), maxOccupancy)
        );
    }

    /** If minPrice == null than spec is ignored **/
    public static Specification<Event> priceGreaterThan(BigDecimal minPrice) {
        return ((root, query, criteriaBuilder) ->
                minPrice == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.greaterThan(root.get("price"), minPrice)
        );
    }

    /** If maxPrice == null than spec is ignored **/
    public static Specification<Event> priceLesserThan(BigDecimal maxPrice) {
        return ((root, query, criteriaBuilder) ->
                maxPrice == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.lessThan(root.get("price"), maxPrice)
        );
    }

    /** If organizerId== null than spec is ignored **/
    public static Specification<Event> hasOrganizerId(Long organizerId) {
        return ((root, query, criteriaBuilder) ->
                organizerId == null ?
                        criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get("organizer_id"), organizerId)
        );
    }

    public static Specification<Event> onlyActive(boolean getActiveOnly) {
        return ((root, query, criteriaBuilder) ->
                getActiveOnly ?
                        criteriaBuilder.equal(root.get("active"), true) :
                        criteriaBuilder.conjunction()

        );
    }
}
