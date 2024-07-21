import React, {useEffect, useState} from "react";
import "./App.css";
import {ButtonGroup, Card, createTheme, Text, ThemeProvider} from "@rneui/themed";
import {NumericFormat} from 'react-number-format';

import {StyleSheet, View} from "react-native";
import {Client} from '@stomp/stompjs';

const theme = createTheme({});

function App() {
    const client = new Client({
        brokerURL: 'ws://localhost:8081/state',
        onConnect: () => {
        },
    });
    client.activate();

    const pumpEventChanged = (state: PumpState) => {
        client.publish({destination: '/app/pump/' + state.id + '/state', body: JSON.stringify(state)})
    };

    return (
        <>
            <style type="text/css">{`
        @font-face {
          font-family: 'MaterialIcons';
          src: url(${require("react-native-vector-icons/Fonts/MaterialIcons.ttf")}) format('truetype');
        }

        @font-face {
          font-family: 'FontAwesome';
          src: url(${require("react-native-vector-icons/Fonts/FontAwesome.ttf")}) format('truetype');
        }
      `}</style>
            <ThemeProvider theme={theme}>
                <div className="App">
                    <View style={styles.container}>
                        <Pump id={1} onChange={pumpEventChanged}/>
                        <Pump id={2} onChange={pumpEventChanged}/>
                        <Pump id={3} onChange={pumpEventChanged}/>
                    </View>
                </div>
            </ThemeProvider>
        </>
    );
}

interface PumpProps {
    id: number,
    onChange: ((state: PumpState) => void)
}

interface PumpState {
    id: number,
    litres: number,
    grade: number,
    gradeName: string,
    event: number,
    eventName: string
}

const Pump = (props: PumpProps) => {
    const [start, setStart] = useState(false);
    const [register, setRegister] = useState(false);
    const PETROLS = ['E5', 'E10', 'Diesel'];
    const EVENTS = ['Take the nozzle', 'Pull the trigger', 'Release the trigger', 'Return the nozzle'];
    const getEmptyState = () => {
        return {
            id: props.id,
            litres: 0.0,
            grade: -1,
            gradeName: "",
            event: -1,
            eventName: ""
        }
    }
    const [pumpState, setPumpState] = useState<PumpState>(getEmptyState());

    useEffect(() => {
        if (register) {
            return;
        }
        registerPump(props.id, PETROLS).then((data) => {
            setRegister(true)
        }).catch((e) => {
            console.error(e);
            setRegister(false)
        });

    }, []);

    useEffect(() => {
        if (!start) {
            return;
        }
        const id = setInterval(() => {
            let newPumpState = {
                id: props.id,
                litres: pumpState.litres + Math.random(),
                grade: pumpState.grade,
                gradeName: pumpState.gradeName,
                event: pumpState.event,
                eventName: pumpState.eventName,
            };
            setPumpState(newPumpState);
            props.onChange(newPumpState);

        }, 1500);
        return () => clearInterval(id);
    }, [start, pumpState]);

    return (
        <>
            <Card containerStyle={styles.pump}>
                <Card.Title>Pump #{props.id}</Card.Title>
                <Card.Divider/>
                <Text h1>
                    <NumericFormat value={pumpState.litres}
                                   displayType={"text"}
                                   allowLeadingZeros
                                   allowedDecimalSeparators={['.']} decimalScale={2}
                    />
                </Text>
                <Text h3>Grade of gasoline</Text>
                <ButtonGroup
                    buttons={PETROLS}
                    selectedIndex={pumpState.grade}
                    onPress={(value, k) => {
                        let newPumpSTate = {
                            id: props.id,
                            litres: pumpState.litres,
                            grade: value,
                            gradeName: PETROLS[value],
                            event: pumpState.event,
                            eventName: pumpState.eventName
                        };
                        setPumpState(newPumpSTate);
                        props.onChange(newPumpSTate);
                    }}
                    containerStyle={{marginBottom: 20}}
                />
                <Text h3>Event</Text>
                <ButtonGroup
                    buttons={EVENTS}
                    selectedIndex={pumpState.event}
                    onPress={(value) => {

                        let newPumpState = {
                            id: props.id,
                            litres: pumpState.litres,
                            grade: pumpState.grade,
                            gradeName: pumpState.gradeName,
                            event: value,
                            eventName: EVENTS[value]
                        };
                        if (value === 1) {
                            setStart(true);

                        } else if (value === 2) {
                            setStart(false);

                        } else if (value === 3) {
                            setStart(false);
                            newPumpState = getEmptyState();
                        }

                        setPumpState(newPumpState);
                        props.onChange(newPumpState);
                    }}
                    textStyle={{fontSize: 12}}
                    containerStyle={{marginBottom: 20}}
                />

            </Card>
        </>
    );
};

async function registerPump(id: number, petrols: string[]): Promise<{}> {
    const response = await fetch('http://localhost:8081/pump/' + id, {
        // mode: 'no-cors',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({id: id, petrols: petrols})
    });
    return await response.json();
}

const styles = StyleSheet.create({
    container: {
        flexDirection: "row",
        flex: 2,
        marginTop: 8,
        backgroundColor: 'aliceblue',
    },
    pump: {
        width: 400,

    }
});

export default App;
