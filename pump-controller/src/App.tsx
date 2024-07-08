import React, {useEffect, useState} from "react";
import "./App.css";
import {Text, createTheme, ThemeProvider, Card, ButtonGroup, Icon, useTheme, Theme} from "@rneui/themed";
import {PatternFormat, NumericFormat} from 'react-number-format';
import CountUp from 'react-countup';

import {StyleSheet, View} from "react-native";

const theme = createTheme({});

function App() {
    const [pumpState, setPumpState] = useState<PumpState>();

    const pumpEventChanged = (state: PumpState) => {
        console.log("pump event changed", state);
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
    event: number
}

const Pump = (props: PumpProps) => {
    const [pumpState, setPumpState] = useState<PumpState>({id: props.id, litres: 0.0, grade: -1, event: -1});
    const [start, setStart] = useState(false);

    useEffect(() => {
        if (!start) {
            return;
        }
        const id = setInterval(() => {
            let newPumpState = {
                id: props.id,
                litres: pumpState.litres + Math.random(),
                grade: pumpState.grade,
                event: pumpState.event
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
                    buttons={['E5', 'E10', 'Diesel']}
                    selectedIndex={pumpState.grade}
                    onPress={(value) => {
                        let newPumpSTate = {
                            id: props.id,
                            litres: pumpState.litres,
                            grade: value,
                            event: pumpState.event
                        };
                        setPumpState(newPumpSTate);
                        props.onChange(newPumpSTate);
                    }}
                    containerStyle={{marginBottom: 20}}
                />
                <Text h3>Event</Text>
                <ButtonGroup
                    buttons={['Take the nozzle', 'Pull the trigger', 'Release the trigger', 'Return the nozzle']}
                    selectedIndex={pumpState.event}
                    onPress={(value) => {

                        let newPumpState = {
                            id: props.id,
                            litres: pumpState.litres,
                            grade: pumpState.grade,
                            event: value
                        };
                        if (value === 1) {
                            setStart(true);

                        } else if (value === 2) {
                            setStart(false);

                        } else if (value === 3) {
                            newPumpState = {id: props.id, litres: 0.0, grade: -1, event: -1};
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
