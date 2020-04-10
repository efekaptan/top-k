import React from 'react';
import { Navbar, Container, Nav } from 'react-bootstrap';

export default function () {
    return (
        <Navbar bg="light" expand="lg">
            <Container>
                <Navbar.Brand href="#/">Top Terms of Twitter</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mr-auto">
                        <Nav.Link href="https://medium.com/@efekaptan/top-k-frequent-items-in-a-time-sliding-window" target="_blank">Article</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}