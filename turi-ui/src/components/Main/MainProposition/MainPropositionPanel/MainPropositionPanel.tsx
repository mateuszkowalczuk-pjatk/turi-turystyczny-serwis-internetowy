import { useNavigate } from 'react-router-dom'
import TextBold from '../../../Shared/Controls/Text/TextBold'
import { SearchMode } from '../../../../types/offer.ts'
import { TouristicPlaceType } from '../../../../types/touristicPlace.ts'
import { AttractionType } from '../../../../types/attraction.ts'
import styles from './MainPropositionPanel.module.css'

interface Props {
    text: string
    imagePath: string
    mode?: SearchMode
    touristicPlaceType?: TouristicPlaceType
    attractionType?: AttractionType
}

const MainPropositionPanel = ({ text, imagePath, mode, touristicPlaceType, attractionType }: Props) => {
    const navigate = useNavigate()

    const handleSearch = () => {
        navigate(
            '/search' +
                '?mode=' +
                mode +
                (touristicPlaceType != null ? '&touristicPlaceType=' + touristicPlaceType : '') +
                (attractionType != null ? '&attractionType=' + attractionType : '')
        )
    }

    return (
        <div
            className={styles.panel}
            style={{ backgroundImage: `url(${imagePath})` }}
            role="button"
            tabIndex={0}
            onClick={handleSearch}
        >
            <TextBold text={text} />
        </div>
    )
}

export default MainPropositionPanel
