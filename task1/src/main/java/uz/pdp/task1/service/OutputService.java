package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Client;
import uz.pdp.task1.entity.Currency;
import uz.pdp.task1.entity.Output;
import uz.pdp.task1.entity.Warehouse;
import uz.pdp.task1.payload.ApiResponse;
import uz.pdp.task1.payload.OutputDto;
import uz.pdp.task1.repository.ClientRepository;
import uz.pdp.task1.repository.CurrencyRepository;
import uz.pdp.task1.repository.OutputRepository;
import uz.pdp.task1.repository.WarehouseRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    @Autowired
    OutputRepository outputRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    ClientRepository clientRepository;

    public List<Output> getOutputs(){
        return outputRepository.findAll();
    }

    public Output getOutput(Integer id){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        return optionalOutput.orElseGet(Output::new);
    }

    public ApiResponse addOutput(OutputDto outputDto){
        Timestamp timestamp = Timestamp.from(Instant.now());

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha warehouse topilmadi!", false);
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new ApiResponse("Kiritilgan id li currency topilmadi!", false);
        Currency currency = optionalCurrency.get();

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent())
            return new ApiResponse("Kiritilgan id li client topilmadi!", false);
        Client client = optionalClient.get();

        int code = 0;
        List<Output> outputList = outputRepository.findAll();
        if (!outputList.isEmpty()){
            code = Integer.parseInt(outputList.get(outputList.size()-1).getCode())+1;
        }else {
            code = 1;
        }

        Output output = new Output();
        output.setClient(client);
        output.setCode(String.valueOf(code));
        output.setDate(timestamp);
        output.setCurrency(currency);
        output.setWarehouse(warehouse);
        output.setFactureNumber(outputDto.getFactureNUmber());
        outputRepository.save(output);
        return new ApiResponse("Output qo'shildi!", true);
    }

    public ApiResponse editOutput(Integer id, OutputDto outputDto){
        Optional<Output> optionalOutput = outputRepository.findById(id);
        if (!optionalOutput.isPresent())
            return new ApiResponse("Kiritilgan id li output topilmadi!", false);
        Output output = optionalOutput.get();

        Optional<Warehouse> optionalWarehouse = warehouseRepository.findById(outputDto.getWarehouseId());
        if (!optionalWarehouse.isPresent())
            return new ApiResponse("Kiritilgan id bo'yicha warehouse topilmadi!", false);
        Warehouse warehouse = optionalWarehouse.get();

        Optional<Currency> optionalCurrency = currencyRepository.findById(outputDto.getCurrencyId());
        if (!optionalCurrency.isPresent())
            return new ApiResponse("Kiritilgan id li currency topilmadi!", false);
        Currency currency = optionalCurrency.get();

        Optional<Client> optionalClient = clientRepository.findById(outputDto.getClientId());
        if (!optionalClient.isPresent())
            return new ApiResponse("Kiritilgan id li client topilmadi!", false);
        Client client = optionalClient.get();

        output.setClient(client);
        output.setCurrency(currency);
        output.setWarehouse(warehouse);
        output.setFactureNumber(outputDto.getFactureNUmber());
        outputRepository.save(output);
        return new ApiResponse("Output taxrirlandi!", true);
    }

    public ApiResponse deleteOutput(Integer id){
        try {
            outputRepository.deleteById(id);
            return new ApiResponse("Output o'chirildi!", true);
        }catch (Exception e){
            return new ApiResponse("Xatolik!!!", false);
        }
    }

}
