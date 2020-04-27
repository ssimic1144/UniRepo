# simple_dapp

## TODO

- Doradit izgled

## Project setup
```
npm install
```

## Firebase

You need to create your own firestore database and import configuration settings in src/config.js

## Truffle

You will need to deploy local blockchain to interact with..

```
truffle develop

truffle migrate --reset
```

After that you will have to update contract address in src/web3.js with new one provided by truffle


### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
