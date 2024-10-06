import { FaMapMarkerAlt } from "react-icons/fa";
import styles from './Map.module.css';

const Map = () => {
    return (
        <div className={styles.map}>
            <FaMapMarkerAlt size={50}/>
        </div>
    )
}

export default Map;