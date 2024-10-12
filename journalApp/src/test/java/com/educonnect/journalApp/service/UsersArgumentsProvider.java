package com.educonnect.journalApp.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.educonnect.journalApp.entity.User;

public class UsersArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext arg0) throws Exception {

        return Stream.of(Arguments.of(User.builder().userName("ram").password("ram").build()),
                Arguments.of(User.builder().userName("sham").password("sham").build()));
    }

}
