/** @type {import('ts-jest').JestConfigWithTsJest} **/
const { compilerOptions } = require('./tsconfig');
const {pathsToModuleNameMapper} = require("ts-jest");
module.exports = {
  rootDir: ".",
  clearMocks: true,
  testPathIgnorePatterns: ["/node_modules/", "/dist/"],
  roots: ['<rootDir>'],
  modulePaths: [compilerOptions.baseUrl],
  moduleNameMapper: pathsToModuleNameMapper(compilerOptions.paths /*, { prefix: '<rootDir>/' } */),
  testEnvironment: "allure-jest/node",
  setupFilesAfterEnv: ['./jest.setup.ts'],
  transform: {
    "^.+.tsx?$": ["ts-jest",{}],
  },
};