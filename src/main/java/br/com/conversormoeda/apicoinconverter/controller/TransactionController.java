package br.com.conversormoeda.apicoinconverter.controller;

import br.com.conversormoeda.apicoinconverter.dto.RequestCoinDTO;
import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.service.ITransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

/**
 * Controller of transaction
 *
 * @author mcrj
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = {"/transaction"}, produces = MediaType.APPLICATION_JSON_VALUE)
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully transaction"),
        @ApiResponse(code = 400, message = "Occurred a fail trying to reach the result")
})
@RequiredArgsConstructor
public class TransactionController {

    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final ITransactionService transactionService;


    @ApiOperation(value = "View transaction processed by currency conversion", response = TransactionFinalDTO.class)
    @PostMapping
    public ResponseEntity<TransactionFinalDTO> getCoinConverter(final @RequestBody RequestCoinDTO coinDTO) {

        logger.info("Coin converter for transaction DTO");
        final TransactionFinalDTO transactionFinalDTO = this.transactionService
                .processTransactionDTO(coinDTO.getUserId(), coinDTO.getCoinDestiny(), coinDTO.getValue());

        return Objects.isNull(transactionFinalDTO)
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(transactionFinalDTO);
    }

    @ApiOperation(value = "View all transaction processed by user ID", response = TransactionFinalDTO.class
            , responseContainer = "List")
    @GetMapping("/{userId}")
    public ResponseEntity<Collection<TransactionFinalDTO>> getAllTransactionByUser(final @PathVariable Integer userId) {

        logger.info("Get all transaction by user");
        final Collection<TransactionFinalDTO> collectionTransactionUser =
                this.transactionService.getAllTransactionByUserId(userId);

        return collectionTransactionUser.isEmpty()
                ? ResponseEntity.badRequest().build()
                : ResponseEntity.ok(collectionTransactionUser);
    }
}