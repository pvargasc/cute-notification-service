module.exports = {
    parser: "@babel/eslint-parser",
    parserOptions: {
        requireConfigFile: false,
        babelOptions: {
            presets: ["@babel/preset-env", "@babel/preset-react"]
        }
    },
    env: {
      browser: true,
      es2021: true,
      node: true,
    },
    root: true,
    extends: [
      'eslint:recommended',
      'prettier',
      'plugin:react/recommended',
      'plugin:prettier/recommended',
      'plugin:react-hooks/recommended',
    ],
    plugins: ['prettier', 'react', 'react-hooks'],
    rules: {
      'prefer-const': 'warn',
      'no-var': 'warn',
      'no-unused-vars': 'warn',
      'object-shorthand': 'warn',
      "quotes": "off",
      'react/jsx-fragments': ['warn', 'syntax'],
      'react/jsx-filename-extension': [
        'warn',
        {
          extensions: ['js', 'jsx'],
        },
      ],
      'react-hooks/rules-of-hooks': 'error',
      'react-hooks/exhaustive-deps': 'warn',
      'react/react-in-jsx-scope': 'off',
      'react/prop-types': 'off',
      'prettier/prettier': 'warn',
    },
    settings: {
      react: {
        version: 'detect',
      },
    },
  };