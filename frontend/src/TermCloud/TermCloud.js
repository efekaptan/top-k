import React, { Component } from 'react';
import WordCloud from 'wordcloud';

class TermCloud extends Component {
    WIDTH = 640;
    HEIGTH = 480;

    state = {
        data: [],
        hasPropsChanged: false,
        width: this.WIDTH,
        height: this.HEIGTH
    };

    wordCloudRef = React.createRef();

    static getDerivedStateFromProps(props, state) {
        let hasPropsChanged = false;
        if (props.data !== state.data) {
            hasPropsChanged = true;
        }
        return { data: props.data, hasPropsChanged };
    }

    componentDidMount() {
        this.setCanvasDimensions();
        window.addEventListener("resize", this.setCanvasDimensions);
    }

    setCanvasDimensions = () => {
        const innerWidth = window.innerWidth;
        const isBigScreen = innerWidth > this.WIDTH;

        this.setState({
            width: isBigScreen ? this.WIDTH : innerWidth / 1.25,
            height: isBigScreen ? this.HEIGTH : innerWidth / 2
        }, this.renderWordCloud);
    }

    renderWordCloud() {
        const { data } = this.state;
        if (data.length <= 0) return;
        const min = data[data.length - 1].count;
        const max = data[0].count;
        const list = data.map(item => [item.term, item.count]);
        WordCloud(this.wordCloudRef.current, {
            list,
            weightFactor: (count) => this.getWeight(min, max, count),
        });
    }

    getWeight(min, max, count) {
        const { width } = this.state;
        const minWeight = width / 30;
        const maxWeight = width / 8;
        const diff = max - min;
        const newDiff = maxWeight - minWeight;
        return (count * newDiff - min * newDiff + minWeight * diff) / diff;
    }

    render() {
        const { width, height } = this.state;

        if (this.state.hasPropsChanged) {
            this.renderWordCloud();
        }

        return (
            <canvas id="wordCloud" width={width} height={height} ref={this.wordCloudRef}>
            </canvas>
        )
    }
}

export default TermCloud;