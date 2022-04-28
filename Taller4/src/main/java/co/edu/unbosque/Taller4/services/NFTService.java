package co.edu.unbosque.Taller4.services;

import co.edu.unbosque.Taller4.dtos.NFT;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NFTService {

    public List<NFT> getNFT() throws IOException {

        List<NFT> nfts;

        try (InputStream is = NFTService.class.getClassLoader()
                .getResourceAsStream("nfts.csv")){

            HeaderColumnNameMappingStrategy<NFT> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(NFT.class);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){

                CsvToBean<NFT> csvToBean = new CsvToBeanBuilder<NFT>(br)
                        .withType(NFT.class)
                        .withMappingStrategy(strategy)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                nfts = csvToBean.parse();
            }
        }
        return nfts;
    }

    public NFT createNFT(String username, String role, String titulo, String money, String path) throws IOException {
        titulo = RandomStringUtils.randomAlphabetic(10);
        String newLine = "\n"+ username +","+ role +","+ titulo +","+ money;
        FileOutputStream fos = new FileOutputStream(path + "WEB-INF/classes/" + "nfts.csv", true);
        fos.write(newLine.getBytes(StandardCharsets.UTF_8));
        fos.close();

        return new NFT(titulo, money, path);
    }
}
