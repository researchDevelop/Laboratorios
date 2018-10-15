var chai = require('chai');
var chaiHttp = require('chai-http');
var {server} = require('../index');

var expect = chai.expect;

chai.use(chaiHttp);

describe('App', function() {
  describe('/GET Users', () => {
    it('it should GET all the users', (done) => {
      chai.request(server)
          .get('/v1/user')
          .end((err, res) => {
                expect(res).to.have.status(200);
                expect(res.body).to.be.an('array');
            done();
          });
    });
});
});