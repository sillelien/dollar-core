package com.sillelien.dollar;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

import java.io.File;
import java.io.IOException;

/**
 * @author hello@neilellis.me
 */
public class DocTest {

    @Test
    public void test() throws IOException {
        PegDownProcessor pegDownProcessor = new PegDownProcessor(Extensions.FENCED_CODE_BLOCKS);
        PegDownProcessor processor = new PegDownProcessor(Extensions.FENCED_CODE_BLOCKS);
        RootNode rootNode = processor.parseMarkdown(FileUtils.readFileToString(new File("README.md")).toCharArray());
        rootNode.accept(new DocTestingVisitor());

    }
}
