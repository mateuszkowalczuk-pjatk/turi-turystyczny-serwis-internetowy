import { useHooks } from '../../../../hooks/shared/useHooks.ts'
import TextBold from '../../../Shared/Controls/Text/TextBold'
import { SearchMode } from '../../../../types/offer.ts'
import { AttractionType } from '../../../../types/attraction.ts'
import { TouristicPlaceType } from '../../../../types/touristicPlace.ts'
import styles from './MainPropositionPanel.module.css'

interface Props {
    text: string
    mode?: SearchMode
    touristicPlaceType?: TouristicPlaceType
    attractionType?: AttractionType
}

const MainPropositionPanel = ({ text, mode, touristicPlaceType, attractionType }: Props) => {
    const { navigate } = useHooks()

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
            className={`${styles.panel} ${
                touristicPlaceType
                    ? styles[touristicPlaceType.toLowerCase()]
                    : attractionType
                      ? styles[attractionType.toLowerCase()]
                      : ''
            }`}
            role="button"
            tabIndex={0}
            onClick={handleSearch}
        >
            <TextBold text={text} />
        </div>
    )
}

export default MainPropositionPanel
