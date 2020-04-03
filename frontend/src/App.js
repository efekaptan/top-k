import React, { Component } from 'react';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import * as config from './config';
import { Container, Row, Col } from 'react-bootstrap';
import FrequencyList from './List/FrequencyList';
import TermList from './List/TermList';
import Navigation from './Navigation';
import TermCloud from './TermCloud';

class App extends Component {
  constructor(props) {
    super(props);
    this.socket = new SockJS(config.SOCKET_URL);
    this.stompClient = Stomp.over(this.socket);
    this.state = {
      data: [],
      terms: []
    };
  }

  componentDidMount() {
    (async () => this.fetchTermPairs())();
    this.poller = setInterval(this.fetchTermPairs, config.POLLING_INTERVAL);
    if (!config.IS_DEBUG) {
      this.stompClient.debug = null;
    }
    this.stompClient.connect({}, this.onConnect);
  }

  onConnect = () => {
    this.subscription = this.stompClient.subscribe(config.TOPIC, this.onMessage);
  }

  onMessage = (message) => {
    this.setState((prevState) => ({
      terms: [message.body, ...prevState.terms.slice(0, config.TABLE_SIZE - 1)]
    }));
  }

  fetchTermPairs = async () => {
    const response = await fetch(`${config.API_URL}/frequency/pairs`);
    const data = await response.json();
    this.setState({ data });
  }

  componentWillUnmount() {
    clearInterval(this.poller);
  }

  render() {
    const { data, terms } = this.state;
    return (
      <>
        <Navigation />
        <Container>
          <Row className="text-center my-3">
            <Col>
              <TermCloud data={data} />
            </Col>
          </Row>
          <Row>
            <Col>
              <TermList terms={terms} />
            </Col>
            <Col>
              <FrequencyList data={data} />
            </Col>
          </Row>
        </Container>
      </>
    );
  }
}

export default App;
