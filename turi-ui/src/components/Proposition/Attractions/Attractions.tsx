import AttractionsText from "./AttractionsText/AttractionsText";
import AttractionsPanels from "./AttractionsPanels/AttractionsPanels";
import styles from './Attractions.module.css';

const Attractions = () => {
    return (
        <div className={styles.attractions}>
            <AttractionsText />
            <AttractionsPanels />
        </div>
    )
}

export default Attractions;