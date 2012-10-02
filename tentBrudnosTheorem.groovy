def tent(x) {
    if (x <= 0.5) {
        2.0 * x
    }
    else {
        2.0 * (1.0 - x)
    }
}

def partitionSymbol(x, partitions) {
    def partition = partitions.find { p -> 
        x >= p.start && x <= p.end 
    }
    partition.symbol
}

def buildTransitionMap(symbolString, tMap) {
    (0 ..< symbolString.size()-1).step(2).each { i ->
        def twogram = symbolString[i .. i+1]
        
        tMap[twogram] = tMap[twogram] ? tMap[twogram] + 1 : 1
    }
    
    tMap
}

def normalizeTransitionMap(tMap, partition) {
    
    partition.each { p ->
        def symbol = p.symbol
        
        def counts = tMap.findAll { k, v ->
            k.startsWith(symbol)
        }.collect{ k, v -> v }.sum()
        
        //println counts
        
        tMap = tMap.each {k, v ->
            if(k.startsWith(symbol)){
                tMap[k] = v / counts
            }
        }
        
    }
    
    tMap
    
}

def NUM_SAMPLES = 5000
def LENGTH_SAMPLE = 50

def x0 = Math.random()
def x  = x0

def samples = []

(0 ..< NUM_SAMPLES).each {
    def sample = [x0]
(0 ..< LENGTH_SAMPLE).each{

   // print partitionSymbol(x, partitions)
    
    x = tent(x)
    
    sample << x
}
   
    samples << sample
   
    x0 = Math.random()
    x  = x0
}


def pA1 = ["start" : 0.0, "end" : 0.49999999, "symbol" : 'A']
def pB1 = ["start" : 0.5, "end" : 1.0, "symbol" : 'B']
def partitions1 = [pA1, pB1]


def pA2 = ["start" : 0.0, "end" : 0.249999999, "symbol" : 'A']
def pB2 = ["start" : 0.25, "end" : 0.74999999, "symbol" : 'B']
def pC2 = ["start" : 0.75, "end" : 1.0, "symbol" : 'C']
def partitions2 = [pA2, pB2, pC2]

def tMap1 = [:]
def tMap2 = ["AA":0, "AB":0, "AC":0, "BA":0, "BB":0, "BC":0, "CA":0, "CB":0, "CC":0] 

samples.each { sample ->
    def partition1String = ""
    def partition2String = ""
    
    sample.each{ value ->

    partition1String += partitionSymbol(value, partitions1)
    partition2String += partitionSymbol(value, partitions2)
    
   
    }
   //println "1 : " + partition1String
   //println "2 : " + partition2String
   //println ""
   
   tMap1 = buildTransitionMap(partition1String, tMap1)
   tMap2 = buildTransitionMap(partition2String, tMap2)
   
   
}

println ""
println normalizeTransitionMap(tMap1, partitions1)
println ""
println normalizeTransitionMap(tMap2, partitions2)
