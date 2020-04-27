const abi = [
  {
    "constant": false,
    "inputs": [
      {
        "internalType": "string",
        "name": "_candID",
        "type": "string"
      },
      {
        "internalType": "string",
        "name": "_candName",
        "type": "string"
      },
      {
        "internalType": "string",
        "name": "_probID",
        "type": "string"
      }
    ],
    "name": "registerCandidateForProblem",
    "outputs": [],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "constant": false,
    "inputs": [
      {
        "internalType": "string",
        "name": "_candID",
        "type": "string"
      },
      {
        "internalType": "string",
        "name": "_probID",
        "type": "string"
      }
    ],
    "name": "vote",
    "outputs": [],
    "payable": false,
    "stateMutability": "nonpayable",
    "type": "function"
  },
  {
    "constant": true,
    "inputs": [
      {
        "internalType": "string",
        "name": "_candID",
        "type": "string"
      },
      {
        "internalType": "string",
        "name": "_probID",
        "type": "string"
      }
    ],
    "name": "getVotesForCandidate",
    "outputs": [
      {
        "internalType": "uint256",
        "name": "",
        "type": "uint256"
      }
    ],
    "payable": false,
    "stateMutability": "view",
    "type": "function"
  }
]

export default abi