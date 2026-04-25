import type { CodegenConfig } from "@graphql-codegen/cli";

const config: CodegenConfig = {
  schema: "../graphql/**/*.graphqls",
  documents: "./graphql-template/**/*.graphql",

  generates: {
    "./graphql-api/types.ts": {
      plugins: [
        "typescript",
        "typescript-operations",
        "typed-document-node"
      ],
    },
  },
};

export default config;