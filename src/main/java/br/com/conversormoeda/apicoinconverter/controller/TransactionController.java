package br.com.conversormoeda.apicoinconverter.controller;

import br.com.conversormoeda.apicoinconverter.dto.TransactionFinalDTO;
import br.com.conversormoeda.apicoinconverter.security.BadRequestException;
import br.com.conversormoeda.apicoinconverter.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

/**
 * Controller of transaction
 *
 * @author mcrj
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping({"/transaction"})
public class TransactionController {

    @SuppressWarnings("unused")
    @Autowired
    private ITransactionService transactionService;

    @GetMapping("/{idUser}/{coinDestiny}/{value}")
    public ResponseEntity<TransactionFinalDTO> getCoinConverter(final @PathVariable Integer idUser,
                                                        final @PathVariable String coinDestiny,
                                                        final @PathVariable BigDecimal value) throws BadRequestException {

        try {
            final TransactionFinalDTO transactionFinalDTO = this.transactionService.processTransactionDTO(idUser, coinDestiny, value);

            return Objects.isNull(transactionFinalDTO)
                    ? ResponseEntity.badRequest().build()
                    : ResponseEntity.ok(transactionFinalDTO);
        } catch (BadRequestException badEx) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badEx.getMessage(), badEx);
        }
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<Collection<TransactionFinalDTO>> getAllTransactionByUser(final @PathVariable Integer idUser) {
        try {
            final Collection<TransactionFinalDTO> collectionTransactionUser =
                    this.transactionService.getAllTransactionByUserId(idUser);

            return collectionTransactionUser.isEmpty()
                    ? ResponseEntity.badRequest().build()
                    : ResponseEntity.ok(collectionTransactionUser);
        } catch (BadRequestException badEx) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, badEx.getMessage(), badEx);
        }
    }
}