import Web3 from "web3";
import abi from "./abi.js"

const web3 = new Web3(window.ethereum);


//update contract address
const newContract = new web3.eth.Contract(abi, "0x952e0459428d1CB055EFaFD80e97Fe5d1a1AC427");



export default newContract
