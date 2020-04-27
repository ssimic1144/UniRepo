pragma solidity >=0.4.21 <0.7.0;

contract Ticket {
    struct Candidate {
        string candidateName;
        uint hasVotes;
        bool exists; 
    }

    struct Voter {
        bool hasVoted;
    }
    
    //

    //dict {problemID : {candidateID : candidate}
    mapping (string =>mapping(string => Candidate)) private problems;
    
    //dict {adresaGlasaca : {problemID : Voter}}    
    mapping ( address => mapping(string => Voter) ) private voters;
    
    //dict {problemID : {candidateID : hasVotedOnThisProblem}}
    mapping(string => mapping(string => bool)) private candidateOnProblem;
    

    function registerCandidateForProblem( string memory _candID, string memory _candName,string memory _probID) public {
        Candidate storage newCand = problems[_probID][_candID];
        

        newCand.candidateName = _candName;
        newCand.hasVotes = 0;
        newCand.exists = true;
        
        candidateOnProblem[_probID][_candID] = true;
        
        
    }
    
    //glasaj za kadnidata koji je na problemu
    function vote (string memory _candID ,string memory _probID) public{
        Voter storage newVoter = voters[msg.sender][_probID];
        
        //provjera da nije vec glasao za taj problem
        require(!voters[msg.sender][_probID].hasVoted);
        //provjera dal kandidat postoji na problemu
        require(problems[_probID][_candID].exists);
        
        newVoter.hasVoted = true;
        problems[_probID][_candID].hasVotes++;
    }
    
    function getVotesForCandidate( string memory _candID, string memory _probID) public view returns(uint){
        return problems[_probID][_candID].hasVotes;        
    }
    
}