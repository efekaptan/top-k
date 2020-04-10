package com.topk.termexporter;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TokenizerTest {
    private List<String> getTokens(String text) {
        return Tokenizer.tokenize(text);
    }

    @Test
    public void shouldReturnTokens() {
        String text = "This man working out to gospel music he’s about to burn this league to the ground https://t.co/x1lPRbVJDS";
        List<String> expected = Arrays.asList("man", "working", "gospel", "music", "burn", "league", "ground");
        Assert.assertEquals(expected, getTokens(text));
    }

    @Test
    public void shouldRemoveEmojis() {
        String text = "I wish you the best \uD83D\uDE0D";
        Assert.assertEquals(Collections.emptyList(), getTokens(text));
    }

    @Test
    public void shouldRemoveMentions() {
        String text = "RT @gifer_art: Mecha #dragonite! I'll be taking a bit of a brake of doing those mechamons.";
        List<String> expected = Arrays.asList("mecha", "dragonite", "taking", "bit", "brake", "mechamons");
        Assert.assertEquals(expected, getTokens(text));
    }

    @Test
    public void shouldRemoveThreeDots() {
        String text = "RT @gorillaz: Meanwhile, at Kong Studios... \uD83E\uDD2A";
        List<String> expected = Arrays.asList("kong", "studios");
        Assert.assertEquals(expected, getTokens(text));

        text = "not…";
        Assert.assertEquals(Collections.emptyList(), getTokens(text));
    }

    @Test
    public void shouldReplaceQuotes() {
        String text = "let’s go!";
        Assert.assertEquals(Collections.emptyList(), getTokens(text));

        text = "\"I'll";
        Assert.assertEquals(Collections.emptyList(), getTokens(text));
    }

    @Test
    public void shouldSkipNumbers() {
        String text = "me 123 123me me123";
        Assert.assertEquals(Arrays.asList("123me", "me123"), getTokens(text));
    }
}
