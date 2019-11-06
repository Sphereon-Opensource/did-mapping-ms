package com.sphereon.ms.did.mapping.utils;

import org.junit.Test;

public class DidUtilsTest {
    @Test
    public void validDidFormatShouldBeValid(){
        String ethDid = "did:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74";
        String fctDid = "did:factom:mainnet:f26e1c422c657521861ced450442d0c664702f49480aec67805822edfcfee758";

        assert(DidUtils.isValidDidFormat(ethDid));
        assert(DidUtils.isValidDidFormat(fctDid));
    }

    @Test
    public void invalideDidFormatShouldBeInvalid(){
        String badMethodDid = "did:bad:f26e1c422c657521861ced450442d0c664702f49480aec67805822edfcfee758";
        String badSchemeDid = "bad:ethr:0xf3beac30c498d9e26865f34fcaa57dbb935b0d74";
        String nullDid = null;

        assert(!DidUtils.isValidDidFormat(badMethodDid));
        assert(!DidUtils.isValidDidFormat(badSchemeDid));
        assert(!DidUtils.isValidDidFormat(nullDid));
    }

}
