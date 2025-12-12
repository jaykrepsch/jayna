package me.jayna.web.rest;

import me.jayna.service.RelationService;
import me.jayna.service.dto.BidirectionalRelationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing bidirectional relations.
 */
@RestController
@RequestMapping("/api/relations")
public class RelationResource {

    private final Logger log = LoggerFactory.getLogger(RelationResource.class);
    private final RelationService relationService;

    public RelationResource(RelationService relationService) {
        this.relationService = relationService;
    }

    /**
     * GET /api/relations/bidirectional/{entityType}/{entityId} : get all bidirectional relations for an entity.
     *
     * @param entityType the entity type
     * @param entityId the entity id
     * @return the ResponseEntity with status 200 (OK) and the list of bidirectional relations
     */
    @GetMapping("/bidirectional/{entityType}/{entityId}")
    public ResponseEntity<List<BidirectionalRelationDTO>> getBidirectionalRelations(
        @PathVariable String entityType,
        @PathVariable Long entityId
    ) {
        log.debug("REST request to get bidirectional relations for {} with id {}", entityType, entityId);
        
        List<BidirectionalRelationDTO> relations = relationService.getBidirectionalRelations(entityType, entityId);
        return ResponseEntity.ok(relations);
    }

    /**
     * POST /api/relations/bidirectional : create a new bidirectional relation.
     *
     * @param relationDTO the relation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new relation
     */
    @PostMapping("/bidirectional")
    public ResponseEntity<BidirectionalRelationDTO> createBidirectionalRelation(
        @RequestBody BidirectionalRelationDTO relationDTO
    ) {
        log.debug("REST request to create bidirectional relation : {}", relationDTO);
        
        BidirectionalRelationDTO result = relationService.createBidirectionalRelation(relationDTO);
        return ResponseEntity.ok(result);
    }

    /**
     * DELETE /api/relations/bidirectional/{id} : delete the bidirectional relation.
     *
     * @param id the id of the relation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bidirectional/{id}")
    public ResponseEntity<Void> deleteBidirectionalRelation(@PathVariable Long id) {
        log.debug("REST request to delete bidirectional relation : {}", id);
        
        try {
            relationService.deleteBidirectionalRelation(id);
            log.debug("Successfully deleted bidirectional relation with ID: {}", id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.warn("Failed to delete bidirectional relation with ID: {} - may not exist: {}", id, e.getMessage());
            // Return 200 OK even if deletion fails, as the relation might not exist
            return ResponseEntity.ok().build();
        }
    }
} 