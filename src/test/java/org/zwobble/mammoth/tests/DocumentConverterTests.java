package org.zwobble.mammoth.tests;

import org.junit.Test;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.documents.Document;
import org.zwobble.mammoth.documents.DocumentElement;
import org.zwobble.mammoth.documents.Text;
import org.zwobble.mammoth.html.Html;
import org.zwobble.mammoth.html.HtmlNode;

import java.util.List;

import static com.natpryce.makeiteasy.MakeItEasy.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.zwobble.mammoth.tests.DeepReflectionMatcher.deepEquals;
import static org.zwobble.mammoth.tests.documents.DocumentElementMakers.*;
import static org.zwobble.mammoth.util.MammothLists.list;

public class DocumentConverterTests {
    @Test
    public void plainParagraphIsConvertedToPlainParagraph() {
        assertThat(
            convertToHtml(make(a(PARAGRAPH, with(CHILDREN, list(runWithText("Hello")))))),
            deepEquals(list(Html.element("p", list(Html.text("Hello"))))));
    }

    @Test
    public void multipleParagraphsInDocumentAreConvertedToMultipleParagraphs() {
        assertThat(
            DocumentConverter.convertToHtml(new Document(list(
                make(a(PARAGRAPH, with(CHILDREN, list(runWithText("Hello"))))),
                make(a(PARAGRAPH, with(CHILDREN, list(runWithText("there")))))))),

            deepEquals(list(
                Html.element("p", list(Html.text("Hello"))),
                Html.element("p", list(Html.text("there"))))));
    }

    @Test
    public void boldRunsAreWrappedInStrongTags() {
        assertThat(
            convertToHtml(make(a(RUN, with(BOLD, true), with(CHILDREN, list(new Text("Hello")))))),
            deepEquals(list(Html.element("strong", list(Html.text("Hello"))))));
    }

    @Test
    public void italicRunsAreWrappedInEmphasisTags() {
        assertThat(
            convertToHtml(make(a(RUN, with(ITALIC, true), with(CHILDREN, list(new Text("Hello")))))),
            deepEquals(list(Html.element("em", list(Html.text("Hello"))))));
    }


    private List<HtmlNode> convertToHtml(DocumentElement element) {
        return DocumentConverter.convertToHtml(element);
    }
}
